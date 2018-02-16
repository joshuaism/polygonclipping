package com.dwilden.polygonclipping.sweepline;

import com.dwilden.polygonclipping.Point;
import com.dwilden.polygonclipping.Segment;
import com.dwilden.polygonclipping.enums.EdgeType;
import com.dwilden.polygonclipping.enums.PolygonType;

import java.util.Iterator;

import static com.dwilden.polygonclipping.Utilities.signedArea;

public class SweepEvent {

    public boolean left;              // is point the left endpoint of the edge (point, otherEvent->point)?
    public Point point;          // point associated with the event
    public SweepEvent otherEvent; // event associated to the other endpoint of the edge
    public PolygonType polygonType;        // Polygon to which the associated segment belongs to
    public int polygon;
    public EdgeType type;
    //The following fields are only used in "left" events
    /**  Does segment (point, otherEvent->p) represent an inside-outside transition in the polygon for a vertical ray from (p.x, -infinite)? */
    public boolean inOut;
    public boolean otherInOut; // inOut transition for the segment from the other polygon preceding this segment in sl
    public Iterator<SweepEvent> posSL; // Position of the event (line segment) in sl
    public SweepEvent prevInResult; // previous segment in sl belonging to the result of the boolean operation
    public boolean inResult;
    public int pos;
    public boolean resultInOut;
    public int contourId;


    public SweepEvent () {}

    public SweepEvent (boolean left, Point point, SweepEvent otherEvent, PolygonType polygonType) {
        this(left, point, otherEvent, polygonType, EdgeType.NORMAL);
    }

    public SweepEvent (boolean left, Point point, SweepEvent otherEvent, PolygonType polygonType, EdgeType edgeType) {
        this.left = left;
        this.point = point;
        this.otherEvent = otherEvent;
        this.polygonType = polygonType;
        this.type = edgeType;
        this.prevInResult = null;
        this.inResult = false;
        this.inOut = false;
        this.otherInOut = false;
        this.contourId = -1;
    }

    public SweepEvent (Point point, boolean left, int polygon, boolean inOut) {
        this.left = left;
        this.point = point;
        this.polygon = polygon;
        this.inOut = inOut;
    }

    // member functions
    /** Is the line segment (point, otherEvent->point) below point p */
    public boolean below(Point p) {
        return (left) ? signedArea(point, otherEvent.point, p) > 0 :
                signedArea(otherEvent.point, point, p) > 0;
    }

    /** Is the line segment (point, otherEvent->point) above point p */
    public boolean above(Point p) {
        return !below(p);
    }

    /** Is the line segment (point, otherEvent->point) a vertical line segment */
    public boolean vertical() {
        return point.x == otherEvent.point.x;
    }

    /** Return the line segment associated to the SweepEvent */
    public Segment segment() {
        return new Segment(point, otherEvent.point);
    }

    @Override
    public String toString() {
        return "SweepEvent{" +
                "left=" + left +
                ", inOut=" + inOut +
                ", point=" + point +
                ", polygon=" + polygon +
                '}';
    }
}