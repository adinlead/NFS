package cc.itez.nfs.utils;

public class StringUtils {
    /**
     * 判断字符串是否为空
     *
     * <pre>
     *  StringUtils.isEmpty(null)        => true
     *  StringUtils.isEmpty("")          => true
     *  StringUtils.isEmpty(" ")         => false
     *  StringUtils.isEmpty("hello")     => false
     *  StringUtils.isEmpty("  hello  ") => false
     * </pre>
     * 参考 {@link org.apache.commons.lang3.StringUtils#isEmpty()}
     *
     * @param str 需要判断的字符串
     * @return str如果为空或者为空字符串，则返回True
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断字符串是否不为空
     *
     * <pre>
     *  StringUtils.isNotEmpty(null)      => false
     *  StringUtils.isNotEmpty("foo")     => true
     *  StringUtils.isNotEmpty("hello")   => true
     *  StringUtils.isNotEmpty(" ")       => true
     * </pre>
     * 参考 {@link org.apache.commons.lang3.StringUtils#isNotEmpty()}
     *
     * @param str 需要判断的字符串
     * @return str如果不为空且不为空字符串，则返回True
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    /**
     * 判断字符串是否为空白
     *
     * <pre>
     *  StringUtils.isBlank(null)        => true
     *  StringUtils.isBlank("")          => true
     *  StringUtils.isBlank(" ")         => true
     *  StringUtils.isBlank("hello")     => false
     *  StringUtils.isBlank("  hello  ") => false
     * </pre>
     * 参考 {@link org.apache.commons.lang3.StringUtils#isBlank()}
     *
     * @param str 需要判断的字符串
     * @return str如果为空白或者为空白字符串，则返回True
     */
    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }


    /**
     * 判断字符串是否不为空白
     *
     * <pre>
     *  StringUtils.isNotBlank(null)         => false
     *  StringUtils.isNotBlank("")           => false
     *  StringUtils.isNotBlank(" ")          => false
     *  StringUtils.isNotBlank("hello")      => true
     *  StringUtils.isNotBlank("  hello  ")  => true
     * </pre>
     * 参考 {@link org.apache.commons.lang3.StringUtils#isNotBlank()}
     *
     * @param str 需要判断的字符串
     * @return str如果不为空白且不为空白字符串，则返回True
     */
    public static boolean isNotBlank(String str) {
        return str != null && !str.isBlank();
    }

    /**
     * 去除字符串尾端字符
     * <pre>
     *  StringUtils.trimEnd(null,null)          => null
     *  StringUtils.trimEnd(null,"lo")          => null
     *  StringUtils.trimEnd("hello","")         => "hello"
     *  StringUtils.trimEnd("hello",null)       => "hello"
     *  StringUtils.trimEnd("hello","lo")       => "hel"
     *  StringUtils.trimEnd("hello","l")        => "hello"
     *  StringUtils.trimEnd("hello","hello")    => ""
     * </pre>
     *
     * @param str  字符串
     * @param tail 尾字符(要删除的尾部字符串)
     * @return 删除尾后的字符串
     */
    public static String trimEnd(String str, String tail) {
        if (isEmpty(str) || isEmpty(tail)) {
            return str;
        }
        while (str.endsWith(tail)) {
            str = str.substring(0, str.lastIndexOf(tail));
        }
        return str;
    }

    /**
     * 去除字符串尾端字符
     * <pre>
     *  StringUtils.trimStart(null,null)        => null
     *  StringUtils.trimStart(null,"lo")        => null
     *  StringUtils.trimStart("hello","")       => "hello"
     *  StringUtils.trimStart("hello",null)     => "hello"
     *  StringUtils.trimStart("hello","he")     => "llo"
     *  StringUtils.trimStart("hello","e")      => "hello"
     *  StringUtils.trimStart("hel","hello")    => "hel"
     *  StringUtils.trimStart("hello","hello")  => ""
     * </pre>
     *
     * @param str  字符串
     * @param head 头字符(要删除的头部字符串)
     * @return 删除尾后的字符串
     */
    public static String trimStart(String str, String head) {
        if (isEmpty(str) || isEmpty(head)) {
            return str;
        }
        while (str.startsWith(head)) {
            str = str.substring(head.length());
        }
        return str;
    }

    /**
     * 查询参考字符串在字符串的开头出现了多少次
     * <pre>
     *     StringUtils.countStart(null,null)        => 0
     *     StringUtils.countStart("","a")           => 0
     *     StringUtils.countStart("ffmpeg",null)    => 0
     *     StringUtils.countStart("ffmpeg","a")     => 0
     *     StringUtils.countStart("ffmpeg","f")     => 2
     *     StringUtils.countStart("ffmpeg-f","f")   => 2
     *     StringUtils.countStart("ffmpeg","ff")    => 1
     *     StringUtils.countStart("ffmpeg","m")     => 0
     *     StringUtils.countStart("ababc","ab")     => 2
     * </pre>
     * 注意！如果参考字符串不存在与开头，那么他将不被统计
     *
     * @return 参考字符串出现次数
     */
    public static int countStart(String str, String refer) {
        if (isEmpty(str) || isEmpty(refer)) {
            return 0;
        }
        int idx = 0;
        int count = 0;
        while (str.startsWith(refer, idx)) {
            idx += refer.length();
            count++;
        }
        return count;
    }

    /**
     * 保留开头字符串
     * <pre>
     * StringUtils.keepStart(null,null,0)       => null
     * StringUtils.keepStart("","",0)           => ""
     * StringUtils.keepStart("aabbaa","b",1)    => "aabbaa"
     * StringUtils.keepStart("aabbaa","a",1)    => "abb"
     * StringUtils.keepStart("aabbaa","a",10)   => "bb"
     * StringUtils.keepStart("aabbaa","a",1)    => "abb"
     * StringUtils.keepStart("aabbaa","aa",1)   => "aabb"
     * StringUtils.keepStart("bbaabb","aa",1)   => "bbaabb"
     * </pre>
     *
     * @param str   字符串
     * @param refer 参考字符串
     * @param n     保留数量
     * @return 保留{@link n}个参考的字符串
     */
    public static String keepStart(String str, String refer, int n) {
        if (isEmpty(str) || isEmpty(refer)) {
            return str;
        }
        str = trimEnd(str, refer);
        if (str.isEmpty()) {
            return str;
        }
        if (n > 0) {
            while (str.startsWith(refer) && countStart(str, refer) > n) {
                str = str.substring(refer.length());
            }
        } else {
            return trimStart(str, refer);
        }
        return str;
    }

    /**
     * 保留开头字符串，若开头无参考字符串或者开头字符串数量达不到保留数量，那么将参考字符串添加到字符串开头
     * <pre>
     * StringUtils.keepStart(null,null,0)       => null
     * StringUtils.keepStart("","",0)           => ""
     * StringUtils.keepStart("aabbaa","b",1)    => "aabbaa"
     * StringUtils.keepStart("aabbaa","a",1)    => "abb"
     * StringUtils.keepStart("aabbaa","a",10)   => "bb"
     * StringUtils.keepStart("aabbaa","a",1)    => "abb"
     * StringUtils.keepStart("aabbaa","aa",1)   => "aabb"
     * </pre>
     *
     * @param str   字符串
     * @param refer 参考字符串
     * @param n     保留数量
     * @return 保留{@link n}个参考的字符串
     */
    public static String keepStart(String str, String refer, int n, boolean prepend) {
        if (isEmpty(str) || isEmpty(refer)) {
            return str;
        }
        str = trimEnd(str, refer);
        if (str.isEmpty()) {
            return str;
        }
        if (n > 0) {
            if (str.startsWith(refer)) {
                int cs = countStart(str, refer);
                if (cs == n) {
                    return str;
                } else if (cs > n) {
                    while (str.startsWith(refer) && countStart(str, refer) > n) {
                        str = str.substring(refer.length());
                    }
                } else if (prepend) {
                    return refer.repeat(Math.max(0, n - cs)) + str;
                }
            } else {
                return refer.repeat(n) + str;
            }
        } else {
            return trimStart(str, refer);
        }
        return str;
    }
}
