package com.tjyw.atom.network.presenter;

/**
 * Created by stephen on 14/08/2017.
 */
public interface IPost {

    int Explain = 1;

    int Naming = 2;

    int PayOrder = 3;

    int PayList = 4;

    int PayPreview = 5;

    int UserRegister = 6;

    int UserLoginCode = 7;

    int UserLogin = 8;

    int FavoriteAdd = 9;

    int FavoriteRemove = 10;

    int FavoriteList = 11;

    interface Pay {

        int PayOrder = 1;

        int PayOrderList = 2;

        int PayOrderNameList = 3;

        int PayPreview = 4;
    }

    interface User {

        int Register = 1;

        int LoginCode = 2;

        int Login = 3;
    }

    interface Client {

        int Init = 1;
    }
}
