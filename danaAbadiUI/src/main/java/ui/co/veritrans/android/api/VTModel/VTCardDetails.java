package ui.co.veritrans.android.api.VTModel;

import java.util.HashMap;
import java.util.Map;

import ui.co.veritrans.android.api.VTUtil.VTConfig;

public class VTCardDetails {
	private String card_number;
    private int card_exp_month;
    private int card_exp_year;
    private String card_cvv;

    private boolean secure;
    private String bank = null;
    private String gross_amount;
    private boolean authorize = false;

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public int getCard_exp_month() {
        return card_exp_month;
    }

    public void setCard_exp_month(int card_exp_month) {
        this.card_exp_month = card_exp_month;
    }

    public int getCard_exp_year() {
        return card_exp_year;
    }

    public void setCard_exp_year(int card_exp_year) {
        this.card_exp_year = card_exp_year;
    }

    public String getCard_cvv() {
        return card_cvv;
    }

    public void setCard_cvv(String card_cvv) {
        this.card_cvv = card_cvv;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getGross_amount() {
        return gross_amount;
    }

    public void setGross_amount(String gross_amount) {
        this.gross_amount = gross_amount;
    }

    public Map<String, String> getParamMap() {
        Map<String, String> parameter = new HashMap<>();

        parameter.put("client_key", VTConfig.CLIENT_KEY);
        parameter.put("card_cvv", getCard_cvv());
        parameter.put("card_number", getCard_number());
        parameter.put("card_exp_month", String.valueOf(getCard_exp_month()));
        parameter.put("card_exp_year", String.valueOf(getCard_exp_year()));

        if(isSecure()) {
            parameter.put("secure", String.valueOf(isSecure()));
            parameter.put("gross_amount", getGross_amount());
        }

        if(isAuthorize()) {
            parameter.put("type", "authorize");
        }

        return parameter;
    }

    public String getParamUrl(){

        String paramUrl = "?card_number="+card_number
                + "&card_exp_month="+card_exp_month
                + "&card_exp_year="+card_exp_year
                + "&card_cvv="+card_cvv
                + "&client_key="+ VTConfig.CLIENT_KEY
                + "&secure="+Boolean.toString(secure)
                + getBankParam()
                + "&gross_amount="+gross_amount;

        if(isAuthorize()) {
            paramUrl += "&type=authorize";
        }

        return paramUrl;
    }

    public String getBankParam(){
        if(bank != null){
            return "&bank="+bank;
        }
        return "";
    }

    public boolean isAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }
}
