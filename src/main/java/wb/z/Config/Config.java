package wb.z.Config;

public class Config {

    private static Config config = null;
    public Config getInstance(){
        if (config == null){
            config = new Config();
        }
        return config;
    }

}
