package com.gls.som.route;

import java.util.ArrayList;

/**
 * Created by pratima on 25/2/16.
 */
public class RouteResponse {
    String status;
    String message;
   ArrayList<RouteBean> listofRoute;

    public ArrayList<RouteBean> getListofRoute() {
        return listofRoute;
    }

    public void setListofRoute(ArrayList<RouteBean> listofRoute) {
        this.listofRoute = listofRoute;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
