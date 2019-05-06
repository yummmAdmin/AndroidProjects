package com.digitaldestino.book_table;
import java.util.HashMap;

public class BookTablePresenter implements BookTableContract.Presenter, BookTableContract.Model.OnFinishedListener {
    private BookTableContract.View bookTableView;
    private BookTableContract.Model bookTableModel;

    public BookTablePresenter(BookTableContract.View bookTableView) {
        this.bookTableView = bookTableView;
        this.bookTableModel = new BookTableModel();
    }

    @Override
    public void onFinished(String status, String msg, String key) {
        if (bookTableView != null) {
            bookTableView.hideProgress();
        }
        bookTableView.setDataToViews(status,msg,key);
    }

    @Override
    public void onFailure(Throwable t) {
        if (bookTableView != null) {
            bookTableView.hideProgress();
        }
        bookTableView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        bookTableView=null;
    }

    @Override
    public void requestBookTable(HashMap<String, String> param) {
        if (bookTableView != null) {
            bookTableView.showProgress();
        }
        bookTableModel.getBookTableDetails(this, param);
    }
}
