package com.mqunar.jonsnow.retrace;


import proguard.classfile.util.ClassUtil;
import proguard.obfuscate.MappingProcessor;
import proguard.obfuscate.MappingReader;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReTrace implements MappingProcessor {
    private static final String REGEX_OPTION = "-regex";
    private static final String VERBOSE_OPTION = "-verbose";
    public static final String STACK_TRACE_EXPRESSION = "(?:\\s*%c:.*)|(?:\\s*at\\s+%c.%m\\s*\\(.*?(?::%l)?\\)\\s*)";
    private static final String REGEX_CLASS = "\\b(?:[A-Za-z0-9_$]+\\.)*[A-Za-z0-9_$]+\\b";
    private static final String REGEX_CLASS_SLASH = "\\b(?:[A-Za-z0-9_$]+/)*[A-Za-z0-9_$]+\\b";
    private static final String REGEX_LINE_NUMBER = "\\b[0-9]+\\b";
    private static final String REGEX_TYPE = "\\b(?:[A-Za-z0-9_$]+\\.)*[A-Za-z0-9_$]+\\b(?:\\[\\])*";
    private static final String REGEX_MEMBER = "<?\\b[A-Za-z0-9_$]+\\b>?";
    private static final String REGEX_ARGUMENTS = "(?:\\b(?:[A-Za-z0-9_$]+\\.)*[A-Za-z0-9_$]+\\b(?:\\[\\])*(?:\\s*,\\s*\\b(?:[A-Za-z0-9_$]+\\.)*[A-Za-z0-9_$]+\\b(?:\\[\\])*)*)?";
    private final String regularExpression;
    private final boolean verbose;
    private final File mappingFile;
    private final File stackTraceFile;
    private Map classMap;
    private Map classFieldMap;
    private Map classMethodMap;

    public ReTrace(String var1, boolean var2, File var3) {
        this(var1, var2, var3, (File)null);
    }

    public ReTrace(String var1, boolean var2, File var3, File var4) {
        this.classMap = new HashMap();
        this.classFieldMap = new HashMap();
        this.classMethodMap = new HashMap();
        this.regularExpression = var1;
        this.verbose = var2;
        this.mappingFile = var3;
        this.stackTraceFile = var4;
    }

    public void init() throws IOException {
        MappingReader mappingReader = new MappingReader(this.mappingFile);
        mappingReader.pump(this);
    }


    public String[] execute(String[] stackTrace){
        StringBuffer stringBuffer = new StringBuffer(this.regularExpression.length() + 32);
        char[] chars = new char[32];
        int var4 = 0;
        int var5 = 0;

        ArrayList<String> result = new ArrayList<String>(stackTrace.length);

        while(true) {
            int var6 = this.regularExpression.indexOf(37, var5);
            if(var6 < 0 || var6 == this.regularExpression.length() - 1 || var4 == chars.length) {
                stringBuffer.append(this.regularExpression.substring(var5));
                Pattern var32 = Pattern.compile(stringBuffer.toString());

                StringBuffer var8 = new StringBuffer(256);
                ArrayList var9 = new ArrayList();
                String var10 = null;

                int index = 0;
                while(true) {
                    String var11 = index < stackTrace.length ? stackTrace[index++]: null;
                    if(var11 == null) {
                        return result.toArray(new String[result.size()]);
                    }

                    Matcher var12 = var32.matcher(var11);
                    if(!var12.matches()) {
                        result.add(var11);
                        System.out.println("var11" + var11);
                    } else {
                        int var13 = 0;
                        String var14 = null;
                        String var15 = null;

                        int var16;
                        int var17;
                        for(var16 = 0; var16 < var4; ++var16) {
                            var17 = var12.start(var16 + 1);
                            if(var17 >= 0) {
                                String var18 = var12.group(var16 + 1);
                                char var19 = chars[var16];
                                switch(var19) {
                                    case 'C':
                                        var10 = this.originalClassName(ClassUtil.externalClassName(var18));
                                        break;
                                    case 'a':
                                        var15 = this.originalArguments(var18);
                                        break;
                                    case 'c':
                                        var10 = this.originalClassName(var18);
                                        break;
                                    case 'l':
                                        var13 = Integer.parseInt(var18);
                                        break;
                                    case 't':
                                        var14 = this.originalType(var18);
                                }
                            }
                        }

                        var16 = 0;
                        var8.setLength(0);
                        var9.clear();

                        for(var17 = 0; var17 < var4; ++var17) {
                            int var34 = var12.start(var17 + 1);
                            if(var34 >= 0) {
                                int var35 = var12.end(var17 + 1);
                                String var20 = var12.group(var17 + 1);
                                var8.append(var11.substring(var16, var34));
                                char var21 = chars[var17];
                                switch(var21) {
                                    case 'C':
                                        var10 = this.originalClassName(ClassUtil.externalClassName(var20));
                                        var8.append(ClassUtil.internalClassName(var10));
                                        break;
                                    case 'a':
                                        var15 = this.originalArguments(var20);
                                        var8.append(var15);
                                        break;
                                    case 'c':
                                        var10 = this.originalClassName(var20);
                                        var8.append(var10);
                                        break;
                                    case 'f':
                                        this.originalFieldName(var10, var20, var14, var8, var9);
                                        break;
                                    case 'l':
                                        var13 = Integer.parseInt(var20);
                                        var8.append(var20);
                                        break;
                                    case 'm':
                                        this.originalMethodName(var10, var20, var13, var14, var15, var8, var9);
                                        break;
                                    case 't':
                                        var14 = this.originalType(var20);
                                        var8.append(var14);
                                }

                                var16 = var35;
                            }
                        }

                        var8.append(var11.substring(var16));
                        System.out.println("var8 :" + var8);
                        result.add(var8.toString());

                        for(var17 = 0; var17 < var9.size(); ++var17) {
                            System.out.println("var9"  + var9.get(var17));
//                            result.add(var9.get(var17).toString());
                        }
                    }
                }


            }

            stringBuffer.append(this.regularExpression.substring(var5, var6));
            stringBuffer.append('(');
            char var7 = this.regularExpression.charAt(var6 + 1);
            switch(var7) {
                case 'C':
                    stringBuffer.append("\\b(?:[A-Za-z0-9_$]+/)*[A-Za-z0-9_$]+\\b");
                    break;
                case 'a':
                    stringBuffer.append("(?:\\b(?:[A-Za-z0-9_$]+\\.)*[A-Za-z0-9_$]+\\b(?:\\[\\])*(?:\\s*,\\s*\\b(?:[A-Za-z0-9_$]+\\.)*[A-Za-z0-9_$]+\\b(?:\\[\\])*)*)?");
                    break;
                case 'c':
                    stringBuffer.append("\\b(?:[A-Za-z0-9_$]+\\.)*[A-Za-z0-9_$]+\\b");
                    break;
                case 'f':
                    stringBuffer.append("<?\\b[A-Za-z0-9_$]+\\b>?");
                    break;
                case 'l':
                    stringBuffer.append("\\b[0-9]+\\b");
                    break;
                case 'm':
                    stringBuffer.append("<?\\b[A-Za-z0-9_$]+\\b>?");
                    break;
                case 't':
                    stringBuffer.append("\\b(?:[A-Za-z0-9_$]+\\.)*[A-Za-z0-9_$]+\\b(?:\\[\\])*");
            }

            stringBuffer.append(')');
            chars[var4++] = var7;
            var5 = var6 + 2;
        }
    }

    private void originalFieldName(String var1, String var2, String var3, StringBuffer var4, List var5) {
        int var6 = -1;
        Map var7 = (Map)this.classFieldMap.get(var1);
        if(var7 != null) {
            Set var8 = (Set)var7.get(var2);
            if(var8 != null) {
                Iterator var9 = var8.iterator();

                label46:
                while(true) {
                    while(true) {
                        ReTrace.FieldInfo var10;
                        do {
                            if(!var9.hasNext()) {
                                break label46;
                            }

                            var10 = (ReTrace.FieldInfo)var9.next();
                        } while(!var10.matches(var3));

                        if(var6 < 0) {
                            var6 = var4.length();
                            if(this.verbose) {
                                var4.append(var10.type).append(' ');
                            }

                            var4.append(var10.originalName);
                        } else {
                            StringBuffer var11 = new StringBuffer();

                            for(int var12 = 0; var12 < var6; ++var12) {
                                var11.append(' ');
                            }

                            if(this.verbose) {
                                var11.append(var10.type).append(' ');
                            }

                            var11.append(var10.originalName);
                            var5.add(var11);
                        }
                    }
                }
            }
        }

        if(var6 < 0) {
            var4.append(var2);
        }

    }

    private void originalMethodName(String var1, String var2, int var3, String var4, String var5, StringBuffer var6, List var7) {
        int var8 = -1;
        Map var9 = (Map)this.classMethodMap.get(var1);
        if(var9 != null) {
            Set var10 = (Set)var9.get(var2);
            if(var10 != null) {
                Iterator var11 = var10.iterator();

                label52:
                while(true) {
                    while(true) {
                        ReTrace.MethodInfo var12;
                        do {
                            if(!var11.hasNext()) {
                                break label52;
                            }

                            var12 = (ReTrace.MethodInfo)var11.next();
                        } while(!var12.matches(var3, var4, var5));

                        if(var8 < 0) {
                            var8 = var6.length();
                            if(this.verbose) {
                                var6.append(var12.type).append(' ');
                            }

                            var6.append(var12.originalName);
                            if(this.verbose) {
                                var6.append('(').append(var12.arguments).append(')');
                            }
                        } else {
                            StringBuffer var13 = new StringBuffer();

                            for(int var14 = 0; var14 < var8; ++var14) {
                                var13.append(' ');
                            }

                            if(this.verbose) {
                                var13.append(var12.type).append(' ');
                            }

                            var13.append(var12.originalName);
                            if(this.verbose) {
                                var13.append('(').append(var12.arguments).append(')');
                            }

                            var7.add(var13);
                        }
                    }
                }
            }
        }

        if(var8 < 0) {
            var6.append(var2);
        }

    }

    private String originalArguments(String var1) {
        StringBuffer var2 = new StringBuffer();
        int var3 = 0;

        while(true) {
            int var4 = var1.indexOf(44, var3);
            if(var4 < 0) {
                var2.append(this.originalType(var1.substring(var3).trim()));
                return var2.toString();
            }

            var2.append(this.originalType(var1.substring(var3, var4).trim())).append(',');
            var3 = var4 + 1;
        }
    }

    private String originalType(String var1) {
        int var2 = var1.indexOf(91);
        return var2 >= 0?this.originalClassName(var1.substring(0, var2)) + var1.substring(var2):this.originalClassName(var1);
    }

    private String originalClassName(String var1) {
        String var2 = (String)this.classMap.get(var1);
        return var2 != null?var2:var1;
    }

    public boolean processClassMapping(String var1, String var2) {
        this.classMap.put(var2, var1);
        return true;
    }

    public void processFieldMapping(String var1, String var2, String var3, String var4) {
        Object var5 = (Map)this.classFieldMap.get(var1);
        if(var5 == null) {
            var5 = new HashMap();
            this.classFieldMap.put(var1, var5);
        }

        Object var6 = (Set)((Map)var5).get(var4);
        if(var6 == null) {
            var6 = new LinkedHashSet();
            ((Map)var5).put(var4, var6);
        }

        ((Set)var6).add(new ReTrace.FieldInfo(var2, var3));
    }

    public void processMethodMapping(String var1, int var2, int var3, String var4, String var5, String var6, String var7) {
        Object var8 = (Map)this.classMethodMap.get(var1);
        if(var8 == null) {
            var8 = new HashMap();
            this.classMethodMap.put(var1, var8);
        }

        Object var9 = (Set)((Map)var8).get(var7);
        if(var9 == null) {
            var9 = new LinkedHashSet();
            ((Map)var8).put(var7, var9);
        }

        ((Set)var9).add(new ReTrace.MethodInfo(var2, var3, var4, var6, var5));
    }

    public static void main(String[] var0) {

        String var1 = "(?:\\s*%c:.*)|(?:\\s*at\\s+%c.%m\\s*\\(.*?(?::%l)?\\)\\s*)";
        boolean var2 = false;

        File mappingFile = new File("C:/Users/ironman.li/Desktop/mapping.txt");
        File stackTrace = new File("C:/Users/ironman.li/Desktop/stack.txt");

        ReTrace reTrace = new ReTrace(var1, var2, mappingFile, stackTrace);

        String[] stack = new String[3];
        stack[0] = "at java.util.ArrayList.throwIndexOutOfBoundsException(ArrayList.java:255)";
        stack[1]="at java.util.ArrayList.get(ArrayList.java:308)";
        stack[2]="at com.mqunar.atom.flight.a.e.y.a(SourceFile:100)";
        try {
            reTrace.init();
            String[] result = reTrace.execute(stack);
            String[] result2 = reTrace.execute(stack);
            reTrace.execute(stack);
            System.out.println(Arrays.toString(result));
            System.out.println(Arrays.toString(result2));
        } catch (IOException var8) {
            if(var2) {
                var8.printStackTrace();
            } else {
                System.err.println("Error: " + var8.getMessage());
            }

            System.exit(1);
        }

        System.exit(0);
    }

    private static class MethodInfo {
        private int firstLineNumber;
        private int lastLineNumber;
        private String type;
        private String arguments;
        private String originalName;

        private MethodInfo(int var1, int var2, String var3, String var4, String var5) {
            this.firstLineNumber = var1;
            this.lastLineNumber = var2;
            this.type = var3;
            this.arguments = var4;
            this.originalName = var5;
        }

        private boolean matches(int var1, String var2, String var3) {
            return (var1 == 0 || this.firstLineNumber <= var1 && var1 <= this.lastLineNumber || this.lastLineNumber == 0) && (var2 == null || var2.equals(this.type)) && (var3 == null || var3.equals(this.arguments));
        }
    }

    private static class FieldInfo {
        private String type;
        private String originalName;

        private FieldInfo(String var1, String var2) {
            this.type = var1;
            this.originalName = var2;
        }

        private boolean matches(String var1) {
            return var1 == null || var1.equals(this.type);
        }
    }
}
