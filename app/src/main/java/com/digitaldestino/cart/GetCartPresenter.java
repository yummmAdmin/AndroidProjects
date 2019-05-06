package com.digitaldestino.cart;
import com.digitaldestino.modelClass.get_cart.CartData;
import java.util.ArrayList;
import java.util.HashMap;

public class GetCartPresenter implements GetCartContract.Presenter, GetCartContract.Model.OnFinishedListener {
    private GetCartContract.View cartDataView;
    private GetCartContract.Model cartDataModel;

    public GetCartPresenter(GetCartContract.View cartDataView) {
        this.cartDataView = cartDataView;
        cartDataModel = new GetCartModel();
    }
    @Override
    public void onFinished(ArrayList<CartData> getCartArrayList, String status, String msg, String key,String cart_sum,String service_tax) {
        if (cartDataView != null) {
            cartDataView.hideProgress();
        }
        cartDataView.setDataToRecyclerView(getCartArrayList,status,msg,key,cart_sum,service_tax);
    }

    @Override
    public void onFailure(Throwable t) {
        cartDataView.onResponseFailure(t);
        if (cartDataView != null) {
            cartDataView.hideProgress();
        }else {
            cartDataView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        cartDataView=null;
    }


    @Override
    public void requestgetCartList(HashMap<String, String> param) {
        if (cartDataView != null) {
            cartDataView.showProgress();
        }
        cartDataModel.getCartList(this,param);
    }
}
