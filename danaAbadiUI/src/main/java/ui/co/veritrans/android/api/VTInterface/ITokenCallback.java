package ui.co.veritrans.android.api.VTInterface;

import ui.co.veritrans.android.api.VTModel.VTToken;

public interface ITokenCallback {
	public void onSuccess(VTToken token);
    public void onError(Exception error);
}
