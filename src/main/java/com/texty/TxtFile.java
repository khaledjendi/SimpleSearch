/**
 * @author Khaled Jendi
 */

package com.texty;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TxtFile {
    private static Logger log = Logger.getLogger(TxtFile.class.getName());
    private String txtFileName;
    private String txtFileContent;
    private int score;

    public String getTxtFileName() {
        return txtFileName;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }

    public String getTxtFileContent() {
        return txtFileContent;
    }

    public void setTxtFileContent(String txtFileContent) {
        this.txtFileContent = txtFileContent;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public TxtFile(String txtFileName, String txtFileContent) {
        PropertyConfigurator.configure("log4j.properties");
        score = 0;
        this.txtFileName = txtFileName;
        this.txtFileContent = txtFileContent;
    }


}
