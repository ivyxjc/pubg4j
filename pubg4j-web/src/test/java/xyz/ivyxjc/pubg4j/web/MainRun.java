package xyz.ivyxjc.pubg4j.web;

import java.lang.reflect.Field;
import xyz.ivyxjc.pubg4j.web.entity.PubgParticipant;

/**
 * @author Ivyxjc
 * @since 4/26/2018
 */
public class MainRun {

    public static void main(String[] args) {
        Field[] fields = PubgParticipant.class.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field f : fields) {
            sb.append(fieldNameToDbName(f.getName()));
            sb.append("    ");

            switch (f.getType().getSimpleName()) {
                case "String":
                    sb.append("VARCHAR(100)");
                    break;
                case "Integer":
                    sb.append("INT");
                    break;
                case "Double":
                    sb.append("Decimal(20,12)");
                    break;
                default:
                    sb.append(f.getType().getSimpleName());
                    break;
            }
            sb.append(" NULL");
            sb.append(",");
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    private static String fieldNameToDbName(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append("`");
        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i))) {
                sb.append("_");
                sb.append(Character.toUpperCase(s.charAt(i)));
            } else {
                sb.append(Character.toUpperCase(s.charAt(i)));
            }
        }
        sb.append("`");
        return sb.toString();
    }
}
