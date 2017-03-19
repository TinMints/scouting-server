package net.tinmints.scounting.server.model;

public class ScoutData {
    private String recorder = "";
    private int matchNumber = 0;
    private int teamNumber = 0;
    private String teamAlliance = "";

    private boolean autoStartInMiddle = false;
    private boolean autoCrossedLine = false;
    private boolean autoHighBoiler = false;
    private boolean autoLowBoiler = false;
    private boolean autoMadePeg = false;
    private int autoRotors = 0;

    private int teleLowBoiler = 0;
    private int teleHighBoiler = 0;
    private int teleFuelFloor = 0;
    private int teleFuelStation = 0;
    private int teleHopperTrigger = 0;
    private int teleGearsDeliverd = 0;
    private int teleGearsGot = 0;
    private int teleGearsPickedUp = 0;
    private int teleRotors = 0;
    private int telePilotRate = 0;
    private boolean teleLifts = false;
    private boolean teleOffense = false;
    private boolean teleDefense = false;
    private boolean teleBreakdown = false;
    private boolean teleRecover = false;

    private int score = 0;
    private int fouls = 0;

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getTeamAlliance() {
		return teamAlliance;
	}

	public void setTeamAlliance(String teamAlliance) {
		this.teamAlliance = teamAlliance;
	}

	public boolean isAutoStartInMiddle() {
        return autoStartInMiddle;
    }

    public void setAutoStartInMiddle(boolean autoStartInMiddle) {
        this.autoStartInMiddle = autoStartInMiddle;
    }

    public boolean isAutoCrossedLine() {
        return autoCrossedLine;
    }

    public void setAutoCrossedLine(boolean autoCrossedLine) {
        this.autoCrossedLine = autoCrossedLine;
    }

    public boolean isAutoHighBoiler() {
        return autoHighBoiler;
    }

    public void setAutoHighBoiler(boolean autoHighBoiler) {
        this.autoHighBoiler = autoHighBoiler;
    }

    public boolean isAutoLowBoiler() {
        return autoLowBoiler;
    }

    public void setAutoLowBoiler(boolean autoLowBoiler) {
        this.autoLowBoiler = autoLowBoiler;
    }

    public boolean isAutoMadePeg() {
        return autoMadePeg;
    }

    public void setAutoMadePeg(boolean autoMadePeg) {
        this.autoMadePeg = autoMadePeg;
    }

    public int getAutoRotors() {
        return autoRotors;
    }

    public void setAutoRotors(int autoRotors) {
        this.autoRotors = autoRotors;
    }

    public int getTeleLowBoiler() {
        return teleLowBoiler;
    }

    public void setTeleLowBoiler(int teleLowBoiler) {
        this.teleLowBoiler = teleLowBoiler;
    }

    public int getTeleHighBoiler() {
        return teleHighBoiler;
    }

    public void setTeleHighBoiler(int teleHighBoiler) {
        this.teleHighBoiler = teleHighBoiler;
    }

    public int getTeleFuelFloor() {
        return teleFuelFloor;
    }

    public void setTeleFuelFloor(int teleFuelFloor) {
        this.teleFuelFloor = teleFuelFloor;
    }

    public int getTeleFuelStation() {
        return teleFuelStation;
    }

    public void setTeleFuelStation(int teleFuelStation) {
        this.teleFuelStation = teleFuelStation;
    }

    public int getTeleHopperTrigger() {
        return teleHopperTrigger;
    }

    public void setTeleHopperTrigger(int teleHopperTrigger) {
        this.teleHopperTrigger = teleHopperTrigger;
    }

    public int getTeleRotors() {
        return teleRotors;
    }

    public void setTeleRotors(int teleRotors) {
        this.teleRotors = teleRotors;
    }

    public boolean isTeleLifts() {
        return teleLifts;
    }

    public int getTelePilotRate() {
        return telePilotRate;
    }

    public void setTelePilotRate(int telePilotRate) {
        this.telePilotRate = telePilotRate;
    }

    public void setTeleLifts(boolean teleLifts) {
        this.teleLifts = teleLifts;
    }

    public boolean isTeleOffense() {
        return teleOffense;
    }

    public void setTeleOffense(boolean teleOffense) {
        this.teleOffense = teleOffense;
    }

    public boolean isTeleDefense() {
        return teleDefense;
    }

    public void setTeleDefense(boolean teleDefense) {
        this.teleDefense = teleDefense;
    }

    public boolean isTeleBreakdown() {
        return teleBreakdown;
    }

    public void setTeleBreakdown(boolean teleBreakdown) {
        this.teleBreakdown = teleBreakdown;
    }

    public boolean isTeleRecover() {
        return teleRecover;
    }

    public void setTeleRecover(boolean teleRecover) {
        this.teleRecover = teleRecover;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFouls() {
        return fouls;
    }

    public void setFouls(int fouls) {
        this.fouls = fouls;
    }

    public int getTeleGearsGot() {
        return teleGearsGot;
    }

    public void setTeleGearsGot(int teleGearsGot) {
        this.teleGearsGot = teleGearsGot;
    }

    public int getTeleGearsPickedUp() {
        return teleGearsPickedUp;
    }

    public void setTeleGearsPickedUp(int teleGearsPickedUp) {
        this.teleGearsPickedUp = teleGearsPickedUp;
    }

    public int getTeleGearsDeliverd() {
        return teleGearsDeliverd;
    }

    public void setTeleGearsDeliverd(int teleGearsDeliverd) {
        this.teleGearsDeliverd = teleGearsDeliverd;
    }
	
	
}
