package battle;

import enums.Invulnerabilty;
import pokémon.Pokémon;
import ui.GameFrame;

import java.util.ArrayList;

public class Side {

    private static final int TAILWIND_LENGTH = 4;
    private static final int MAX_TOXIC_SPIKES = 2;
    private static final int MAX_SPIKES = 3;

    //Meta-variables

    private boolean isDouble;
    private BattleSlot slotOne;
    private BattleSlot slotTwo;
    private GameFrame.BattlePrinter battlePrinter;
    private Side opposingSide;

    //Variables relating to the battle state
    private boolean isTailwindActive;
    private int tailwindTurns;
    private int spikeCount;
    private boolean stealthRocksUp;
    private boolean lightScreenActive;
    private int lightScreenTimer;
    private boolean reflectActive;
    private int reflectTimer;
    private int poisonSpikeCount;
    private boolean stickyWebUp;
    private boolean safeguardUp;
    private int safeguardCount;


    public Side(Pokémon mon, GameFrame.BattlePrinter battlePrinter) {
        slotOne = new BattleSlot(mon, this);
        slotTwo = null;
        this.battlePrinter = battlePrinter;
        isDouble = false;
        setup();
    }

    public Side(Pokémon monOne, Pokémon monTwo, GameFrame.BattlePrinter battlePrinter) {
        slotOne = new BattleSlot(monOne, this);
        slotTwo = new BattleSlot(monTwo, this);
        this.battlePrinter = battlePrinter;
        isDouble = true;
        setup();
    }

    private void setup() {
        lightScreenActive = false;
        reflectActive = false;
        lightScreenTimer = 0;
        reflectTimer = 0;
        stealthRocksUp = false;
        spikeCount = 0;
        poisonSpikeCount = 0;
        stickyWebUp = false;
        safeguardUp = false;
        safeguardCount = 0;
    }

    public BattleSlot getSlotOne() {
        return slotOne;
    }

    public BattleSlot getSlotTwo() {
        return slotTwo;
    }

    /**
     * Get the slot that is not the same one as the argument passed in
     * @param slot Should be either slotOne or slotTwo
     * @return slotTwo if slotOne is passed, slotTwo otherwise
     */
    public BattleSlot getSisterSlot(BattleSlot slot) {
        /*
        If slot two is passed, we know it mst be a double battle, or slotTwo would be null.
        Therefore, slotOne is ALWAYS safe to return
        Otherwise, we return slotTwo, which may be null
         */
        if (slot.equals(slotTwo)) {
            return slotOne;
        } else {
            return slotTwo;
        }
    }

    //gets all BattleSlots that exist
    public ArrayList<BattleSlot> getSlots() {
        ArrayList<BattleSlot> slots = new ArrayList<>();
        slots.add(slotOne);
        if (slotTwo != null) {
            slots.add(slotTwo);
        }
        return slots;
    }

    //gets all BattleSlots with Pokémon in them
    public ArrayList<BattleSlot> getSlotsSafe() {
        ArrayList<BattleSlot> slots = new ArrayList<>();
        if (slotOne.getPokémon() != null && !slotOne.isFainted()) {
            slots.add(slotOne);
        }
        if (slotTwo != null && slotTwo.getPokémon() != null && !slotTwo.isFainted()) {
            slots.add(slotTwo);
        }
        return slots;
    }

    public Side getOpposingSide() {
        return opposingSide;
    }

    public void setOpposingSide(Side opposingSide) {
        this.opposingSide = opposingSide;
    }

    public void addOpponents() {
        for (BattleSlot slot : opposingSide.getSlots()) {
            for (BattleSlot ourSlot : this.getSlots()) {
                ourSlot.addOpponent(slot.getPokémon());
            }
        }
    }

    public boolean isTailwindActive() {
        return isTailwindActive;
    }

    public void activateTailwind() {
        isTailwindActive = true;
        tailwindTurns = TAILWIND_LENGTH;
    }

    public boolean decrementTailwind() {
        tailwindTurns--;
        if (tailwindTurns == 0) {
            isTailwindActive = false;
            return true;
        } else {
            return false;
        }
    }

    public void startTurn() {
        for (BattleSlot slot : getSlots()) {
            slot.startTurn();
            slot.setUpRolls();
        }
    }

    public boolean contains(Pokémon mon) {
        if (slotOne.getOriginal().equals(mon)) {
            return true;
        } else if (slotTwo != null && slotTwo.getOriginal().equals(mon)) {
            return true;
        } else {
            return false;
        }
    }

    public void updateOpponents(boolean done) {
        for (BattleSlot ourSlot : getSlotsSafe()) {
            for (BattleSlot theirSlot : opposingSide.getSlotsSafe()) {
                ourSlot.addOpponent(theirSlot.getPokémon());
            }
        }
        if (!done) {
            opposingSide.updateOpponents(true);
        }
    }

    public int getSpikeCount() {
        return spikeCount;
    }

    public void setSpikeCount(int spikeCount) {
        this.spikeCount = spikeCount;
    }

    public boolean isStealthRocksUp() {
        return stealthRocksUp;
    }

    public void setStealthRocksUp(boolean stealthRocksUp) {
        this.stealthRocksUp = stealthRocksUp;
    }

    public boolean isLightScreenActive() {
        return lightScreenActive;
    }

    public void setLightScreenActive(boolean lightScreenActive) {
        this.lightScreenActive = lightScreenActive;
    }


    public boolean isReflectActive() {
        return reflectActive;
    }

    public void setReflectActive(boolean reflectActive) {
        this.reflectActive = reflectActive;
    }

    public int getPoisonSpikeCount() {
        return poisonSpikeCount;
    }

    public void setPoisonSpikeCount(int poisonSpikeCount) {
        this.poisonSpikeCount = poisonSpikeCount;
    }

    public boolean isStickyWebUp() {
        return stickyWebUp;
    }

    public void setStickyWebUp(boolean stickyWebUp) {
        this.stickyWebUp = stickyWebUp;
    }

    public boolean isSafeguardUp() {
        return safeguardUp;
    }

    public boolean decLightScreen() {
        if (lightScreenActive) {
            lightScreenTimer--;
            if (lightScreenTimer == 0) {
                lightScreenActive = false;
                return true;
            }
        }
        return false;
    }
    public boolean decReflect() {
        if (reflectActive) {
            reflectTimer--;
            if (reflectTimer == 0) {
                reflectActive = false;
                return true;
            }
        }
        return false;
    }

    public void resetHazards() {
        stealthRocksUp = false;
        spikeCount = 0;
        poisonSpikeCount = 0;
        stickyWebUp = false;
    }

    public void activateSafeguard() {
        safeguardUp = true;
        safeguardCount = 5;
    }

    public boolean decrementSafeguard() {
        safeguardCount--;
        if (safeguardCount == 0) {
            safeguardUp = false;
            return true;
        } else {
            return false;
        }
    }

    public boolean addToxicSpikes() {
        if (poisonSpikeCount < MAX_TOXIC_SPIKES) {
            poisonSpikeCount++;
            return true;
        } else {
            return false;
        }
    }

    public void activateLightScreen(int duration) {
        lightScreenActive = true;
        lightScreenTimer = duration;
    }

    public void activateReflect(int duration) {
        reflectActive = true;
        reflectTimer = duration;
    }

    public void endTurnUpdate(boolean player) {
        if (decrementTailwind()) {
            battlePrinter.printTailwindOver(player);
        }
        if (decrementSafeguard()) {
            battlePrinter.printSafeguardEnd(player);
        }
        if (decReflect()) {
            battlePrinter.printReflectOver(player);
        }
        if (decLightScreen()) {
            battlePrinter.printLightScreenOver(player);
        }
        for (BattleSlot slot : getSlotsSafe()) {
            slot.resetFlinch();
            if (slot.getInvulnerabilty().equals(Invulnerabilty.PROTECTED)) {
                slot.setInvulnerabilty(Invulnerabilty.NONE);
            }
            if (slot.decrementTaunt()) {
                battlePrinter.printTauntEnded(slot);
            }
            if (slot.decrementEncore()) {
                battlePrinter.printEncoreEnded(slot);
            }
            slot.setForceEndEncore(false);

        }
//        slotOne.resetFlinch();
//        if (slotTwo != null) {
//            slotTwo.resetFlinch();
//        }
//        if (slotOne.getInvulnerabilty().equals(Invulnerabilty.PROTECTED)) {
//            slotOne.setInvulnerabilty(Invulnerabilty.NONE);
//        }
//        if (slotTwo != null && slotTwo.getInvulnerabilty().equals(Invulnerabilty.PROTECTED)) {
//            slotTwo.setInvulnerabilty(Invulnerabilty.NONE);
//        }


    }

    public void integrate() {
        slotOne.integrate();
        if (slotTwo != null) {
            slotTwo.integrate();
        }
    }
}
