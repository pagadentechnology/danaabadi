package ui.co.veritrans.android.api.VTModel;

public class VTTwoClicksCardDetails extends VTCardDetails {
    private boolean isTwoClicks;
    private String tokenId;

    public boolean isTwoClicks() {
        return isTwoClicks;
    }

    public void setIsTwoClicks(boolean isTwoClicks) {
        this.isTwoClicks = isTwoClicks;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
