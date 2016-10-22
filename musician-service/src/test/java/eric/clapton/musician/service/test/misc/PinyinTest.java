package eric.clapton.musician.service.test.misc;

import org.junit.Assert;
import org.junit.Test;

import eric.clapton.musician.service.util.PinyinUtils;

public class PinyinTest {
    @Test
    public void test1() throws Throwable {
        Assert.assertEquals(null, PinyinUtils.toPinyin(null));
        Assert.assertEquals("", PinyinUtils.toPinyin(""));

        System.out.println(PinyinUtils
                .toPinyin("The quick brown fox jumps over the lazy dog."));
        System.out.println(PinyinUtils.toPinyin("天涯旧恨，独自凄凉人不问。"
                + "欲见回肠，断尽金炉小篆香。" + " 黛蛾长敛，任是春风吹不展。" + "困倚危楼，过尽飞鸿字字愁。"));
        System.out.println(PinyinUtils.toPinyin(Character
                .toString((char) 0x4E6F)));
        System.out.println(PinyinUtils.toPinyin("上海浅水湾文化艺术中心"));
        System.out.println(PinyinUtils.toPinyin("Ivy Young")); // abcd
    }

}
