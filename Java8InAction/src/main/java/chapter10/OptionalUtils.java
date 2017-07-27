package chapter10;

import java.util.Optional;

/**
 * Author by darcy
 * Date on 17-7-27 下午9:01.
 * Description:
 */
public class OptionalUtils {

    /**
     * 空的Optional对象, 对遭遇无法转换的String时返回的非法值进行建模.
     * @param str
     * @return
     */
    public static Optional<Integer> stringToInt(String str) {
        try {
            return Optional.ofNullable(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
