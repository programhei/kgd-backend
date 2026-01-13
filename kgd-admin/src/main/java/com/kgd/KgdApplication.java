package com.kgd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author kgd
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class KgdApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(KgdApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  无人机自主空战对抗场景集质量评估工具启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "        ,--.                          \n" +
                "   ,--/  /|  ,----..       ,---,     \n" +
                ",---,': / ' /   /   \\    .'  .' `\\   \n" +
                ":   : '/ / |   :     : ,---.'     \\  \n" +
                "|   '   ,  .   |  ;. / |   |  .`\\  | \n" +
                "'   |  /   .   ; /--`  :   : |  '  | \n" +
                "|   ;  ;   ;   | ;  __ |   ' '  ;  : \n" +
                ":   '   \\  |   : |.' .''   | ;  .  | \n" +
                "|   |    ' .   | '_.' :|   | :  |  ' \n" +
                "'   : |.  \\'   ; : \\  |'   : | /  ;  \n" +
                "|   | '_\\.''   | '/  .'|   | '` ,/   \n" +
                "'   : |    |   :    /  ;   :  .'     \n" +
                ";   |,'     \\   \\ .'   |   ,.'       \n" +
                "'---'        `---`     '---'         \n");
    }
}
