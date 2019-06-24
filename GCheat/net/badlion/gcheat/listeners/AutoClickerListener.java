package net.badlion.gcheat.listeners;

import org.bukkit.entity.*;
import net.badlion.gcheat.*;
import java.util.regex.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import net.badlion.gcheat.gemu.events.*;
import org.bukkit.event.*;
import java.util.*;

public class AutoClickerListener implements Listener
{
    public static int CPS_THRESHOLD;
    public static int CPS_THRESHOLD_EXPERIMENTAL;
    public static int CPS_EXPERIMENTAL;
    public static int CPS_LOW;
    public static int CPS_INVALID;
    public static int CPS_VERY_INVALID;
    public static int CPS_BAN_THIS_NIGGER;
    public static int INSTA_BAN;
    public static Map<Player, Long> lastTickWithPacketSent;
    public static Map<Player, Boolean> gotLastTickPacket;
    public static Map<Player, Long> experimentalHitsSinceLastCheck;
    public static Map<Player, Long> lastTickCheck;
    public static Map<Player, Long> hitsSinceLastCheck;
    private static Map<UUID, List<Long>> highDetectionTimes;
    private static Map<UUID, List<Long>> fastDetectionTimes;
    private static Map<UUID, List<Long>> longDetectionTimes;
    private GCheat plugin;
    private static Pattern p;
    
    public AutoClickerListener(final GCheat plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        AutoClickerListener.lastTickCheck.put(event.getPlayer(), 0L);
        AutoClickerListener.hitsSinceLastCheck.put(event.getPlayer(), 0L);
        AutoClickerListener.lastTickWithPacketSent.put(event.getPlayer(), 0L);
        AutoClickerListener.gotLastTickPacket.put(event.getPlayer(), false);
        AutoClickerListener.experimentalHitsSinceLastCheck.put(event.getPlayer(), 0L);
        AutoClickerListener.highDetectionTimes.put(event.getPlayer().getUniqueId(), new ArrayList<Long>());
        AutoClickerListener.fastDetectionTimes.put(event.getPlayer().getUniqueId(), new ArrayList<Long>());
        AutoClickerListener.longDetectionTimes.put(event.getPlayer().getUniqueId(), new ArrayList<Long>());
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        AutoClickerListener.lastTickCheck.remove(event.getPlayer());
        AutoClickerListener.hitsSinceLastCheck.remove(event.getPlayer());
        AutoClickerListener.lastTickWithPacketSent.remove(event.getPlayer());
        AutoClickerListener.gotLastTickPacket.remove(event.getPlayer());
        AutoClickerListener.experimentalHitsSinceLastCheck.remove(event.getPlayer());
        AutoClickerListener.highDetectionTimes.remove(event.getPlayer().getUniqueId());
        AutoClickerListener.fastDetectionTimes.remove(event.getPlayer().getUniqueId());
        AutoClickerListener.longDetectionTimes.remove(event.getPlayer().getUniqueId());
    }
    
    @EventHandler
    public void onPlayerClick(final PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
            this.handleExperimentalAutoClicking(event.getPlayer(), "C");
            this.handleAutoClicking(event.getPlayer(), "A");
        }
    }
    
    @EventHandler
    public void onPlayerAttack(final PlayerAttackEvent event) {
        this.handleExperimentalAutoClicking(event.getPlayer(), "D");
        this.handleAutoClicking(event.getPlayer(), "B");
    }
    
    private void handleAutoClicking(final Player player, final String type) {
        final Long lastTickCheck = AutoClickerListener.lastTickCheck.get(player);
        Long hitsSinceLastCheck = AutoClickerListener.hitsSinceLastCheck.get(player);
        if (lastTickCheck + 20L <= MinecraftServer.currentTick) {
            if (hitsSinceLastCheck >= AutoClickerListener.CPS_VERY_INVALID) {
                Bukkit.getPluginManager().callEvent((Event)new GCheatEvent(player, GCheatEvent.Type.AUTO_CLICKER, GCheatEvent.Level.ADMIN, player.getName() + " is using Autoclicker Type " + type + " VL[" + hitsSinceLastCheck + "]"));
            }
            else if (hitsSinceLastCheck >= AutoClickerListener.CPS_INVALID) {
                Bukkit.getPluginManager().callEvent((Event)new GCheatEvent(player, GCheatEvent.Type.AUTO_CLICKER, GCheatEvent.Level.ADMIN, player.getName() + " is using Autoclicker Type " + type + " VL[" + hitsSinceLastCheck + "]"));
            }
            else if (hitsSinceLastCheck >= AutoClickerListener.CPS_LOW) {
                Bukkit.getPluginManager().callEvent((Event)new GCheatEvent(player, GCheatEvent.Type.AUTO_CLICKER, GCheatEvent.Level.ADMIN, player.getName() + " is using Autoclicker Type " + type + " VL[" + hitsSinceLastCheck + "]"));
            }
            else if (hitsSinceLastCheck >= AutoClickerListener.CPS_THRESHOLD) {
                Bukkit.getPluginManager().callEvent((Event)new GCheatEvent(player, GCheatEvent.Type.AUTO_CLICKER, GCheatEvent.Level.ADMIN, player.getName() + " is using Autoclicker Type E VL[" + hitsSinceLastCheck + "]"));
            }
            AutoClickerListener.hitsSinceLastCheck.put(player, 0L);
        }
        else {
            ++hitsSinceLastCheck;
            AutoClickerListener.hitsSinceLastCheck.put(player, hitsSinceLastCheck);
        }
    }
    
    private void handleExperimentalAutoClicking(final Player player, final String type) {
        final Long lastTickCheck = AutoClickerListener.lastTickCheck.get(player);
        final Long lastHitPacketReceivedTick = AutoClickerListener.lastTickWithPacketSent.get(player);
        final Boolean gotAlreadyThisTick = AutoClickerListener.gotLastTickPacket.get(player);
        Long hitsSinceLastCheck = AutoClickerListener.experimentalHitsSinceLastCheck.get(player);
        if (lastHitPacketReceivedTick != MinecraftServer.currentTick) {
            AutoClickerListener.gotLastTickPacket.put(player, false);
            AutoClickerListener.experimentalHitsSinceLastCheck.put(player, ++hitsSinceLastCheck);
        }
        else if (!gotAlreadyThisTick) {
            AutoClickerListener.gotLastTickPacket.put(player, true);
        }
        else {
            AutoClickerListener.experimentalHitsSinceLastCheck.put(player, ++hitsSinceLastCheck);
        }
        if (lastTickCheck + 20L <= MinecraftServer.currentTick) {
            if (hitsSinceLastCheck < AutoClickerListener.CPS_EXPERIMENTAL) {
                if (hitsSinceLastCheck >= AutoClickerListener.CPS_THRESHOLD_EXPERIMENTAL) {
                    Bukkit.getPluginManager().callEvent((Event)new GCheatEvent(player, GCheatEvent.Type.AUTO_CLICKER, GCheatEvent.Level.ADMIN, player.getName() + " is using Autoclicker Type F VL[" + hitsSinceLastCheck + "]"));
                }
            }
            AutoClickerListener.experimentalHitsSinceLastCheck.put(player, 0L);
        }
    }
    
    @EventHandler
    public void onPlayerAutoClicking(final GCheatEvent event) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getType:()Lnet/badlion/gcheat/gemu/events/GCheatEvent$Type;
        //     4: getstatic       net/badlion/gcheat/gemu/events/GCheatEvent$Type.AUTO_CLICKER:Lnet/badlion/gcheat/gemu/events/GCheatEvent$Type;
        //     7: if_acmpne       169
        //    10: aload_1         /* event */
        //    11: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getMsg:()Ljava/lang/String;
        //    14: astore_2        /* msg */
        //    15: aload_2         /* msg */
        //    16: ldc             "Type E"
        //    18: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    21: ifne            33
        //    24: aload_2         /* msg */
        //    25: ldc             "Type F"
        //    27: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    30: ifeq            34
        //    33: return         
        //    34: getstatic       net/badlion/gcheat/listeners/AutoClickerListener.p:Ljava/util/regex/Pattern;
        //    37: aload_2         /* msg */
        //    38: invokevirtual   java/util/regex/Pattern.matcher:(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
        //    41: astore_3        /* matcher */
        //    42: aload_3         /* matcher */
        //    43: invokevirtual   java/util/regex/Matcher.find:()Z
        //    46: ifeq            169
        //    49: aload_3         /* matcher */
        //    50: iconst_1       
        //    51: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
        //    54: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //    57: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    60: astore          lvl
        //    62: goto            97
        //    65: astore          e
        //    67: invokestatic    org/bukkit/Bukkit.getLogger:()invokestatic   !!! ERROR
        //    70: new             Ljava/lang/StringBuilder;
        //    73: dup            
        //    74: invokespecial   java/lang/StringBuilder.<init>:()V
        //    77: ldc             "Wrong autoclicker int "
        //    79: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    82: aload_3         /* matcher */
        //    83: iconst_1       
        //    84: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
        //    87: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    90: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    93: invokevirtual   invokevirtual  !!! ERROR
        //    96: return         
        //    97: aload           lvl
        //    99: invokevirtual   java/lang/Integer.intValue:()I
        //   102: bipush          40
        //   104: if_icmple       138
        //   107: getstatic       net/badlion/gcheat/listeners/AutoClickerListener.fastDetectionTimes:Ljava/util/Map;
        //   110: aload_1         /* event */
        //   111: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   114: invokeinterface org/bukkit/entity/Player.getUniqueId:()Ljava/util/UUID;
        //   119: aload_1         /* event */
        //   120: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   123: invokeinterface org/bukkit/entity/Player.getName:()Ljava/lang/String;
        //   128: sipush          10000
        //   131: iconst_5       
        //   132: ldc             " [GCheat] Unfair Advantage"
        //   134: invokestatic    net/badlion/gcheat/GCheat.handleTimeDetection:(Ljava/util/Map;Ljava/util/UUID;Ljava/lang/String;IILjava/lang/String;)Z
        //   137: pop            
        //   138: getstatic       net/badlion/gcheat/listeners/AutoClickerListener.longDetectionTimes:Ljava/util/Map;
        //   141: aload_1         /* event */
        //   142: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   145: invokeinterface org/bukkit/entity/Player.getUniqueId:()Ljava/util/UUID;
        //   150: aload_1         /* event */
        //   151: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   154: invokeinterface org/bukkit/entity/Player.getName:()Ljava/lang/String;
        //   159: ldc             600000
        //   161: bipush          30
        //   163: ldc             " [GCheat] Unfair Advantage"
        //   165: invokestatic    net/badlion/gcheat/GCheat.handleTimeDetection:(Ljava/util/Map;Ljava/util/UUID;Ljava/lang/String;IILjava/lang/String;)Z
        //   168: pop            
        //   169: return         
        //    StackMapTable: 00 06 FC 00 21 07 00 9B 00 FF 00 1E 00 04 07 00 99 07 00 A7 07 00 9B 07 00 A8 00 01 07 00 A9 FC 00 1F 07 00 AA 28 F8 00 1E
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                             
        //  -----  -----  -----  -----  ---------------------------------
        //  49     62     65     97     Ljava/lang/NumberFormatException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Invalid BootstrapMethods attribute entry: 2 additional arguments required for method java/lang/invoke/StringConcatFactory.makeConcatWithConstants, but only 1 specified.
        //     at com.strobel.assembler.ir.Error.invalidBootstrapMethodEntry(Error.java:244)
        //     at com.strobel.assembler.ir.MetadataReader.readAttributeCore(MetadataReader.java:267)
        //     at com.strobel.assembler.metadata.ClassFileReader.readAttributeCore(ClassFileReader.java:261)
        //     at com.strobel.assembler.ir.MetadataReader.inflateAttributes(MetadataReader.java:426)
        //     at com.strobel.assembler.metadata.ClassFileReader.visitAttributes(ClassFileReader.java:1134)
        //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:439)
        //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:377)
        //     at com.strobel.assembler.metadata.MetadataSystem.resolveType(MetadataSystem.java:129)
        //     at com.strobel.assembler.metadata.MetadataSystem.resolveCore(MetadataSystem.java:81)
        //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:104)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:616)
        //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:128)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:626)
        //     at com.strobel.assembler.metadata.MethodReference.resolve(MethodReference.java:177)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2438)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:672)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:655)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:365)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:109)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.base/java.lang.Thread.run(Thread.java:835)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        AutoClickerListener.CPS_THRESHOLD = 1000000;
        AutoClickerListener.CPS_THRESHOLD_EXPERIMENTAL = 1000000;
        AutoClickerListener.CPS_EXPERIMENTAL = 25;
        AutoClickerListener.CPS_LOW = 35;
        AutoClickerListener.CPS_INVALID = 50;
        AutoClickerListener.CPS_VERY_INVALID = 70;
        AutoClickerListener.CPS_BAN_THIS_NIGGER = 100;
        AutoClickerListener.INSTA_BAN = 500;
        AutoClickerListener.lastTickWithPacketSent = new HashMap<Player, Long>();
        AutoClickerListener.gotLastTickPacket = new HashMap<Player, Boolean>();
        AutoClickerListener.experimentalHitsSinceLastCheck = new HashMap<Player, Long>();
        AutoClickerListener.lastTickCheck = new HashMap<Player, Long>();
        AutoClickerListener.hitsSinceLastCheck = new HashMap<Player, Long>();
        AutoClickerListener.highDetectionTimes = new HashMap<UUID, List<Long>>();
        AutoClickerListener.fastDetectionTimes = new HashMap<UUID, List<Long>>();
        AutoClickerListener.longDetectionTimes = new HashMap<UUID, List<Long>>();
        AutoClickerListener.p = Pattern.compile("is using Autoclicker Type [A-Z] VL\\[(\\d+)");
    }
}
