package com.gdgssu.android_deviewsched.model;

import java.util.ArrayList;

public class FavoriteSession {

    private ArrayList<Integer> favorList;

    public FavoriteSession() {
        this.favorList = new ArrayList<>();
    }

    public void selectSession(int sessionId) {
        // ID값이 이미 목록에 있으면 List에서 해당 ID를 삭제.
        if (favorList.contains(sessionId)) {
            for (int i = 0; i < favorList.size(); i++) {
                if (sessionId == favorList.get(i))
                    favorList.remove(i);
            }
        } else {
            favorList.add(sessionId);
        }
    }

    public int getFavorListSize() {
        return favorList.size();
    }

    @Override
    public String toString() {
        return favorList.toString();
    }

    /**
     * favorList를 세팅 혹은 초기화 시킨다.
     * @param favorList
     */
    public void setFavorList(ArrayList<Integer> favorList) {
        this.favorList = favorList;
    }

    public ArrayList<Integer> getFavorList() {
        return favorList;
    }
}
