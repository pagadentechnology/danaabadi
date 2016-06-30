package ui.co.veritrans.android.api;


import ui.co.veritrans.android.api.VTInterface.ITokenCallback;

/**
 * Created by Anis on 11/11/2014.
 */
public class VTWeb extends VTBaseTransactionMethod{


    @Override
    public void preAuthorize() {
        //do nothing, no pre authorize
        throw new UnsupportedOperationException("Don't use pre authorize with VtWeb");
    }

    @Override
    public void capture() {
        throw new UnsupportedOperationException("Don't use capture with VtWeb");
    }

    @Override
    public void charge() {
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    @Override
    public void getToken(ITokenCallback callback) {
        throw new UnsupportedOperationException("Not Implemented Yet");
    }
}
