package com.founder.cdr.hl7.codec;

public enum HL7Charset
{
    ISO_8859_1("8859/1", "ISO-8859-1"),
    ISO_8859_2("8859/2", "ISO-8859-2"),
    ISO_8859_3("8859/3", "ISO-8859-3"),
    ISO_8859_4("8859/4", "ISO-8859-4"),
    ISO_8859_5("8859/5", "ISO-8859-5"),
    ISO_8859_6("8859/1", "ISO-8859-6"),
    ISO_8859_7("8859/1", "ISO-8859-7"),
    ISO_8859_8("8859/1", "ISO-8859-8"),
    ISO_8859_9("8859/1", "ISO-8859-9"),
    ASCII("ASCII", "US-ASCII"),
    BIG_5("BIG-5", "Big5"),
    CNS("CNS 11643-1992", "ISO-2022-CN"),
    GB_1830_2000("GB 18030-2000", ""),
    ISO_IR14("ISO IR14", "ISO-2022-JP"),
    ISO_IR159("ISO IR159", "EUC-JP"),
    ISO_IR87("ISO IR87", "EUC-JP"),
    KS_X_1001("KS X 1001", "EUC-KR"),
    UNICODE("UNICODE", "UTF-8"),
    UTF_16("UNICODE UTF-16", "UTF-16"),
    UTF_32("UNICODE UTF-32", "UTF-32"),
    UTF_8("UNICODE UTF-8", "UTF-8");

    private String hl7CharsetName;
    private String javaCharsetName;

    HL7Charset(String hl7CharsetName, String javaCharsetName) {
        this.hl7CharsetName = hl7CharsetName;
        this.javaCharsetName = javaCharsetName;
    }

    public String getHL7CharsetName() {
        return hl7CharsetName;
    }

    public String getJavaCharsetName() {
        return javaCharsetName;
    }

    /**
     * Returns the HL7Charset that matches the parameter
     *
     * @param s charset string
     * @return HL7Charset enum
     */
    public static HL7Charset getHL7Charset(String s) {
        if (s != null && s.length() > 0) {
            for (HL7Charset charset : HL7Charset.values()) {
                if (charset.hl7CharsetName.equals(s) || charset.javaCharsetName.equals(s)) {
                    return charset;
                }
            }
        }
        return null;
    }

    public static String getCharsetName(String msh18, String defaultCharsetName) {
        HL7Charset charset = HL7Charset.getHL7Charset(msh18);
        return charset != null ? charset.getJavaCharsetName() : defaultCharsetName;
    }
}
