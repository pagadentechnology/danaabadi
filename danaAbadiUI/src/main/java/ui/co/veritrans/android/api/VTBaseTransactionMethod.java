package ui.co.veritrans.android.api;

import java.util.Map;

import ui.co.veritrans.android.api.VTInterface.ITokenCallback;
import ui.co.veritrans.android.api.VTModel.VTBaseTransaction;
import ui.co.veritrans.android.api.VTModel.VTToken;
import retrofit.http.GET;
import retrofit.http.QueryMap;

public abstract class VTBaseTransactionMethod {

	protected VTBaseTransaction transaction;

	public abstract void preAuthorize();

	public abstract void capture();

	public abstract void charge();

	public abstract void getToken(ITokenCallback callback);

	public VTBaseTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(VTBaseTransaction transaction) {
		this.transaction = transaction;
	}

	public interface GetToken {
		@GET("/token")
		VTToken doGetToken(@QueryMap Map<String, String> option);
	}

	public VTBaseTransactionMethod() {
		// TODO Auto-generated constructor stub
	}
}
