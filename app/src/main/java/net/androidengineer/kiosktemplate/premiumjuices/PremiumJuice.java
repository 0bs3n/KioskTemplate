package net.androidengineer.kiosktemplate.premiumjuices;

/**
 * Created by Human on 7/5/2015.
 */
public class PremiumJuice {

    String pjImageFilePath;
    String pjName;
    String pjVGratio;
    String pjPGratio;
    String pjDescription;
    String pjManufacturer;

    public PremiumJuice(String pjimagefilepath,  String pjname, String pjvg, String pjpg,
                        String pjscript,String pjmanufacturer) {
        this.pjImageFilePath = pjimagefilepath;
        this.pjName = pjname;
        this.pjVGratio = pjvg;
        this.pjPGratio = pjpg;
        this.pjDescription = pjscript;
        this.pjManufacturer = pjmanufacturer;

    }

    public String getPjImageFilePath() {
        return pjImageFilePath;
    }

    public void setPjImageFilePath(String filepath) {
        this.pjImageFilePath = filepath;
    }

    public String getPjName() {
        return pjName;
    }

    public void setPjName(String name) {
        this.pjName = name;
    }

    public String getPjVGratio() {
        return pjVGratio;
    }

    public void setPjVGratio(String vgratio) {
        this.pjVGratio = vgratio;
    }

    public String getPjPGratio() {
        return pjPGratio;
    }

    public void setPjPGratio(String pgratio) {
        this.pjPGratio = pgratio;
    }

    public String getPjDescription() {
        return pjDescription;
    }

    public void setPjDescription(String description) {
        this.pjDescription = description;
    }

    public String getPjManufacturer(){
        return pjManufacturer;
    }

    public void setPjManufacturer(String manufacturer){
        this.pjManufacturer = manufacturer;
    }


}
