package com.digitaldestino.bookingtable_list;
import com.digitaldestino.modelClass.book_table.TableData;
import java.util.ArrayList;
import java.util.HashMap;

public class BookingTablePresenter implements BookingTableContract.Presenter, BookingTableContract.Model.OnFinishedListener
{
    private BookingTableContract.View bookingTableView;
    private BookingTableContract.Model bookingTableModel;

    public BookingTablePresenter(BookingTableContract.View bookingTableView) {
        this.bookingTableView = bookingTableView;
        this.bookingTableModel = new BookingTableModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<TableData> tableData) {
        if (bookingTableView != null) {
            bookingTableView.hideProgress();
        }
        bookingTableView.setDataToRecyclerView(status, msg,key,tableData);
    }

    @Override
    public void onFailure(Throwable t) {
        bookingTableView.onResponseFailure(t);
        if (bookingTableView != null) {
            bookingTableView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        bookingTableView=null;
    }

    @Override
    public void requestgetBookTable(HashMap<String, String> param) {
        if (bookingTableView != null) {
            bookingTableView.showProgress();
        }
        bookingTableModel.getBookTableDetails(this, param);
    }
}
