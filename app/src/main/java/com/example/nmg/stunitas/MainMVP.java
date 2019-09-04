package com.example.nmg.stunitas;

import com.example.nmg.stunitas.Data.documents;

import java.util.List;

public class MainMVP {

    public interface Presenter {
        void loadData(String text);
        void createModel();
    }

    public interface View {
        void completed(List<documents> list);
    }

}
