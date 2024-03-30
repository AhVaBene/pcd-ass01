package pcd.ass01.simtraffic.seq.engine;

import pcd.ass01.simtraffic.seq.base.Action;

/**
 * Car agent move forward action
 */
public record MoveForward(double distance) implements Action {}
