package sey.a.rasp3.ui.menu;

public class MenuItems {
    public static final int MORE_DETAILS_OFF = 0;
    public static final int MORE_DETAILS_ON = 1;
    public static final int UPDATE_OFF = 0;
    public static final int UPDATE_ON = 1;
    public static final int HIDE_OFF = 0;
    public static final int HIDE_ON = 1;
    public static final int SHOW_OFF = 0;
    public static final int SHOW_ON = 1;
    public static final int DELETE_OFF = 0;
    public static final int DELETE_ON = 1;

    private int moreDetails = 0;
    private int update = 0;
    private int hide = 0;
    private int show = 0;
    private int delete = 0;

    public MenuItems() {
    }

    public MenuItems(int moreDetails, int update, int hide, int show, int delete) {
        this.moreDetails = moreDetails;
        this.update = update;
        this.hide = hide;
        this.show = show;
        this.delete = delete;
    }

    public int getMoreDetails() {
        return moreDetails;
    }

    public MenuItems setMoreDetails(int moreDetails) {
        this.moreDetails = moreDetails;
        return this;
    }

    public int getUpdate() {
        return update;
    }

    public MenuItems setUpdate(int update) {
        this.update = update;
        return this;
    }

    public int getHide() {
        return hide;
    }

    public MenuItems setHide(int hide) {
        this.hide = hide;
        return this;
    }

    public int getShow() {
        return show;
    }

    public MenuItems setShow(int show) {
        this.show = show;
        return this;
    }

    public int getDelete() {
        return delete;
    }

    public MenuItems setDelete(int delete) {
        this.delete = delete;
        return this;
    }
}
