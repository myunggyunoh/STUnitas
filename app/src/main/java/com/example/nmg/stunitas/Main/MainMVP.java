package com.example.nmg.stunitas.Main;

import com.example.nmg.stunitas.Data.documents;

import java.util.List;

public class MainMVP {

    public interface Presenter {
        void loadData(String text);
        void loadAddData(String text);
    }

    public interface View {
        void search(List<documents> list);
        void addList(List<documents> list);
        void errorToast(String text);
    }

}
