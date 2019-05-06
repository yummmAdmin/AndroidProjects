package com.digitaldestino.bookingtable_list;
import com.digitaldestino.modelClass.book_table.TableData;
import java.util.ArrayList;
import java.util.HashMap;

public interface BookingTableContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, ArrayList<TableData> tableData);

            void onFailure(Throwable t);
        }

        void getBookTableDetails(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(String status, String msg, String key, ArrayList<TableData> tableData);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestgetBookTable(HashMap<String,String>param);
    }
}
