package ui.co.veritrans.android.api;


import android.os.AsyncTask;

import java.util.Map;

import ui.co.veritrans.android.api.VTInterface.ITokenCallback;
import ui.co.veritrans.android.api.VTModel.VTCardDetails;
import ui.co.veritrans.android.api.VTModel.VTToken;
import ui.co.veritrans.android.api.VTUtil.VTConfig;
import retrofit.RestAdapter;


/**
 * Created by Anis on 11/13/2014.
 */

public class VTDirect extends VTBaseTransactionMethod {

    private VTCardDetails card_details;

    @Override
    public void preAuthorize() {
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    @Override
    public void capture() {
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    @Override
    public void charge() {
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    @Override
    public void getToken(ITokenCallback callback) {
        if(callback != null && getCard_details() != null){
            new GetTokenAsync(callback, getCard_details().getParamMap()).execute(VTConfig.getTokenUrl());
        }
    }

    class GetTokenAsync extends AsyncTask<String,Void,Object>{

        ITokenCallback callback;
        Map<String, String> parameter;
        public  GetTokenAsync(ITokenCallback callback, Map<String, String> parameter){
            this.callback = callback;
            this.parameter = parameter;
        }

        @Override
        protected Object doInBackground(String... strings) {
            String url = strings[0];
            try {

                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
                GetToken getToken = restAdapter.create(GetToken.class);

                VTToken token = getToken.doGetToken(this.parameter);

                if(token.getStatus_code() == 200){
                    return token;
                } else {
                    return new Exception(token.getStatus_message());
                }

            } catch (IllegalArgumentException e){
                e.printStackTrace();
                return e;
            } catch (Exception e){
                e.printStackTrace();
                return e;
            }
        }

        @Override
        protected void onPostExecute(Object obj) {
            if(obj instanceof VTToken){
                VTToken token = (VTToken)obj;
                callback.onSuccess(token);
            }else if(obj instanceof Exception){
                Exception ex = (Exception)obj;
                callback.onError(ex);
            }
        }
    }

    public VTCardDetails getCard_details() {
        return card_details;
    }

    public void setCard_details(VTCardDetails card_details) {
        this.card_details = card_details;
    }
}