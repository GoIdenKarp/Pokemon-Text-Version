package moves;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MoveMapper {
    // Static map to cache move name -> Move class mappings
    private static final Map<String, Class<? extends Move>> moveMap = new HashMap<>();
    
    // Static initialization block to register all moves
    static {
        registerMove(Acid.class);
        registerMove(Agility.class);
        registerMove(AirSlash.class);
        registerMove(AquaTail.class);
        registerMove(Assurance.class);
        registerMove(Bite.class);
        registerMove(BodySlam.class);
        registerMove(BugBite.class);
        registerMove(BugBuzz.class);
        registerMove(Captivate.class);
        registerMove(ConfuseRay.class);
        registerMove(Confusion.class);
        registerMove(Crunch.class);
        registerMove(DefenseCurl.class);
        registerMove(Dig.class);
        registerMove(Discharge.class);
        registerMove(DoubleEdge.class);
        registerMove(DoubleKick.class);
        registerMove(DoubleTeam.class);
        registerMove(DragonClaw.class);
        registerMove(DragonRage.class);
        registerMove(DrillPeck.class);
        registerMove(DrillRun.class);
        registerMove(Earthquake.class);
        registerMove(ElectroBall.class);
        registerMove(Ember.class);
        registerMove(Growth.class);
        registerMove(Haze.class);
        registerMove(HeatWave.class);
        registerMove(HelpingHand.class);
        registerMove(HornAttack.class);
        registerMove(HornDrill.class);
        registerMove(Hurricane.class);
        registerMove(HydroPump.class);
        registerMove(HyperFang.class);
        registerMove(Hypnosis.class);
        registerMove(Inferno.class);
        registerMove(IronDefense.class);
        registerMove(KarateChop.class);
        registerMove(LeechSeed.class);
        registerMove(Leer.class);
        registerMove(LightScreen.class);
        registerMove(LowKick.class);
        registerMove(Megahorn.class);
        registerMove(MirrorMove.class);
        registerMove(NastyPlot.class);
        registerMove(Nuzzle.class);
        registerMove(Outrage.class);
        registerMove(Peck.class);
        registerMove(PetalBlizzard.class);
        registerMove(PetalDance.class);
        registerMove(PinMissle.class);
        registerMove(PlayNice.class);
        registerMove(PoisonJab.class);
        registerMove(PoisonPowder.class);
        registerMove(PoisonSting.class);
        registerMove(Protect.class);
        registerMove(Psybeam.class);
        registerMove(Pursuit.class);
        registerMove(QuickAttack.class);
        registerMove(QuiverDance.class);
        registerMove(Rage.class);
        registerMove(RainDance.class);
        registerMove(RapidSpin.class);
        registerMove(RazorLeaf.class);
        registerMove(Reflect.class);
        registerMove(Roar.class);
        registerMove(Roost.class);
        registerMove(Safeguard.class);
        registerMove(SandAttack.class);
        registerMove(ScaryFace.class);
        registerMove(Screech.class);
        registerMove(SeedBomb.class);
        registerMove(SeismicToss.class);
        registerMove(ShadowClaw.class);
        registerMove(SilverWind.class);
        registerMove(SkullBash.class);
        registerMove(Slam.class);
        registerMove(Slash.class);
        registerMove(SleepPowder.class);
        registerMove(Smokescreen.class);
        registerMove(SolarBeam.class);
        registerMove(Spark.class);
        registerMove(StringShot.class);
        registerMove(Struggle.class);
        registerMove(SuckerPunch.class);
        registerMove(SuperFang.class);
        registerMove(Superpower.class);
        registerMove(Supersonic.class);
        registerMove(SweetScent.class);
        registerMove(Swift.class);
        registerMove(SwordsDance.class);
        registerMove(Synthesis.class);
        registerMove(Tackle.class);
        registerMove(TailWhip.class);
        registerMove(Tailwind.class);
        registerMove(TakeDown.class);
        registerMove(Taunt.class);
        registerMove(Thunder.class);
        registerMove(Thunderbolt.class);
        registerMove(Thundershock.class);
        registerMove(ThunderWave.class);
        registerMove(Toxic.class);
        registerMove(ToxicSpikes.class);
        registerMove(Twister.class);
        registerMove(Twineedle.class);
        registerMove(UTurn.class);
        registerMove(Venoshock.class);
        registerMove(VineWhip.class);
        registerMove(WaterGun.class);
        registerMove(WaterPulse.class);
        registerMove(Whirlwind.class);
        registerMove(WildCharge.class);
        registerMove(WillOWisp.class);
        registerMove(WingAttack.class);
        registerMove(Withdraw.class);
        registerMove(WorrySeed.class);
        registerMove(Wrap.class);
    }
    
    /**
     * Maps a move name to its corresponding Move instance
     * @param name The name of the move to create
     * @return A new instance of the corresponding Move class, or null if not found
     */
    public static Move map(String name) {
        try {
            Class<? extends Move> moveClass = moveMap.get(name.toLowerCase().replace(" ", ""));
            if (moveClass != null) {
                return moveClass.getDeclaredConstructor().newInstance();
            }
        } catch (ReflectiveOperationException e) {
            System.err.println("Error creating move instance for: " + name);
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Registers a move class in the mapping
     * @param moveClass The Move class to register
     */
    private static void registerMove(Class<? extends Move> moveClass) {
        String moveName = (String) moveClass.getSimpleName();
        moveMap.put(moveName.toLowerCase(), moveClass);
    }

    /**
     * Gets all registered move names
     * @return Set of all registered move names
     */
    public static Set<String> getAllMoveNames() {
        return new HashSet<>(moveMap.keySet());
    }

    /**
     * Checks if a move name is valid
     * @param name The name to check
     * @return true if the move exists
     */
    public static boolean isValidMove(String name) {
        return moveMap.containsKey(name.toLowerCase());
    }

    /**
     * Gets a random move from the registered moves
     * @return A new instance of a random move
     */
    public static Move getRandomMove() {
        try {
            String[] moves = moveMap.keySet().toArray(new String[0]);
            String randomMoveName = moves[(int)(Math.random() * moves.length)];
            return map(randomMoveName);
        } catch (Exception e) {
            System.err.println("Error creating random move");
            e.printStackTrace();
            return null;
        }
    }
}