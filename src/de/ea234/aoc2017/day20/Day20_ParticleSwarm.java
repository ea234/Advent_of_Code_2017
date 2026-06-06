package de.ea234.aoc2017.day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 20: Particle Swarm ---
 * https://adventofcode.com/2017/day/22
 * 
 * https://www.reddit.com/r/adventofcode/comments/7kz6ik/2017_day_20_solutions/
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * #### Particle   0  MH Distance     -4999849997  avg     -4989906763        4999849997 
 *      Particle   1  MH Distance    -10000099996  avg     -9980213130       10000099996 
 * 
 * p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
 * 
 * Result Part 1 0
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * #### Particle   0  MH Distance     49985400316  avg     49885981010       49985400316 
 * #### Particle   1  MH Distance    -45003745688  avg    -44914251507       45003745688 
 * #### Particle   2  MH Distance     39995398345  avg     39915855856       39995398345 
 *      Particle   3  MH Distance    115003540677  avg    114774839330      115003540677 
 * #### Particle   4  MH Distance    -25012544770  avg    -24962815366       25012544770 
 *      Particle   5  MH Distance     55011942783  avg     54902553677       55011942783 
 * #### Particle   6  MH Distance     20005297130  avg     19965518323       20005297130 
 *      Particle   7  MH Distance    -25022542070  avg    -24972802716       25022542070 
 *      Particle   8  MH Distance    -55006146331  avg    -54896762996       55006146331 
 *      Particle   9  MH Distance    -20006297843  avg    -19966518041       20006297843 
 * #### Particle  10  MH Distance      9989601695  avg      9969725276        9989601695 
 *      Particle  11  MH Distance    -65002246709  avg    -64872980488       65002246709 
 *      Particle  12  MH Distance    -99981599502  avg    -99782750145       99981599502 
 *      Particle  13  MH Distance    -45011639042  avg    -44922137001       45011639042 
 *      Particle  14  MH Distance     69995691559  avg     69856488472       69995691559 
 *      Particle  15  MH Distance     50000092775  avg     49900658843       50000092775 
 *      Particle  16  MH Distance    -35007042234  avg    -34937431536       35007042234 
 *      Particle  17  MH Distance    -54990645578  avg    -54881277665       54990645578 
 *      Particle  18  MH Distance    -10004296889  avg     -9984405844       10004296889 
 *      Particle  19  MH Distance     -9991901601  avg     -9972022894        9991901601 
 *      Particle  20  MH Distance     10007595701  avg      9987701372       10007595701 
 *      Particle  21  MH Distance     44992846252  avg     44903362917       44992846252 
 *      Particle  22  MH Distance     14993650634  avg     14963826802       14993650634 
 *      Particle  23  MH Distance   -150006394011  avg   -149708086145      150006394011 
 *      Particle  24  MH Distance     44992949788  avg     44903466353       44992949788 
 *      Particle  25  MH Distance    -15002647502  avg    -14972814715       15002647502 
 *      Particle  26  MH Distance     55024136031  avg     54914734786       55024136031 
 *      Particle  27  MH Distance     55004842400  avg     54895460358       55004842400 
 *      Particle  28  MH Distance    -79992493773  avg    -79833407103       79992493773 
 *      Particle  29  MH Distance     59983998736  avg     59864694057       59983998736 
 *      Particle  30  MH Distance     25000447119  avg     24950729755       25000447119 
 *      Particle  31  MH Distance    -55007841430  avg    -54898456403       55007841430 
 *      Particle  32  MH Distance     75007339397  avg     74858181335       75007339397 
 *      Particle  33  MH Distance     45009746738  avg     44920246587       45009746738 
 *      Particle  34  MH Distance     35007547458  avg     34937936263       35007547458 
 *      Particle  35  MH Distance    -35022144928  avg    -34952519206       35022144928 
 * #### Particle  36  MH Distance     -9987401993  avg     -9967527763        9987401993 
 *      Particle  37  MH Distance     25030443660  avg     24980696446       25030443660 
 * #### Particle  38  MH Distance      5007648412  avg      4997697417        5007648412 
 *      Particle  39  MH Distance     40015690438  avg     39936127750       40015690438 
 *      Particle  40  MH Distance   -120011683096  avg   -119773030256      120011683096 
 *      Particle  41  MH Distance    -60013189135  avg    -59893855402       60013189135 
 *      Particle  42  MH Distance     55022936412  avg     54913536361       55022936412 
 *      Particle  43  MH Distance     34989549612  avg     34919956327       34989549612 
 *      Particle  44  MH Distance     19996798853  avg     19957028504       19996798853 
 *      Particle  45  MH Distance     79967601955  avg     79808540061       79967601955 
 *      Particle  46  MH Distance    -20015895281  avg    -19976105927       20015895281 
 *      Particle  47  MH Distance     75006744484  avg     74857587019       75006744484 
 *      Particle  48  MH Distance    -90021790221  avg    -89842787631       90021790221 
 *      Particle  49  MH Distance    -25001248386  avg    -24951530226       25001248386 
 *      Particle  50  MH Distance    -50005298203  avg    -49905859097       50005298203 
 *      Particle  51  MH Distance    -54985150926  avg    -54875788486       54985150926 
 *      Particle  52  MH Distance     89993199263  avg     89814225130       89993199263 
 *      Particle  53  MH Distance    -94978051136  avg    -94789148694       94978051136 
 *      Particle  54  MH Distance     39991900425  avg     39912361418       39991900425 
 *      Particle  55  MH Distance    105014545980  avg    104805720454      105014545980 
 *      Particle  56  MH Distance    -39982901870  avg    -39903371818       39982901870 
 *      Particle  57  MH Distance   -130015291097  avg   -129756747909      130015291097 
 * #### Particle  58  MH Distance     -5001449518  avg     -4991504692        5001449518 
 *      Particle  59  MH Distance     49991899453  avg     49892473680       49991899453 
 *      Particle  60  MH Distance    -30008196998  avg    -29948528539       30008196998 
 *      Particle  61  MH Distance     64995448046  avg     64866188591       64995448046 
 *      Particle  62  MH Distance    -55010645381  avg    -54901257568       55010645381 
 *      Particle  63  MH Distance     40018594287  avg     39939028714       40018594287 
 *      Particle  64  MH Distance     30005397500  avg     29945731827       30005397500 
 *      Particle  65  MH Distance     30000898445  avg     29941237250       30000898445 
 *      Particle  66  MH Distance     35018447837  avg     34948825796       35018447837 
 *      Particle  67  MH Distance    -20003599408  avg    -19963822293       20003599408 
 *      Particle  68  MH Distance     65001449237  avg     64872183812       65001449237 
 *      Particle  69  MH Distance     45023347247  avg     44933833564       45023347247 
 *      Particle  70  MH Distance    -25008448873  avg    -24958723549       25008448873 
 *      Particle  71  MH Distance    -14994450373  avg    -14964625745       14994450373 
 *      Particle  72  MH Distance   -110012797588  avg   -109794030420      110012797588 
 *      Particle  73  MH Distance    -55004545476  avg    -54895163733       55004545476 
 *      Particle  74  MH Distance     59991998199  avg     59872685560       59991998199 
 *      Particle  75  MH Distance    -70017291351  avg    -69878066772       70017291351 
 *      Particle  76  MH Distance     25012145349  avg     24962416343       25012145349 
 *      Particle  77  MH Distance    -14999749176  avg    -14969919275       14999749176 
 *      Particle  78  MH Distance    -20010896076  avg    -19971111697       20010896076 
 * #### Particle  79  MH Distance        -5398701  avg        -5393328           5398701 
 *      Particle  80  MH Distance     59967804249  avg     59848515689       59967804249 
 *      Particle  81  MH Distance     -9986902701  avg     -9967028969        9986902701 
 *      Particle  82  MH Distance    -95026337526  avg    -94837387026       95026337526 
 *      Particle  83  MH Distance     19983403449  avg     19943646433       19983403449 
 *      Particle  84  MH Distance    -80024978065  avg    -79865859058       80024978065 
 *      Particle  85  MH Distance     20019389409  avg     19979596573       20019389409 
 *
 *  ...
 *  
 *      Particle 178  MH Distance    125009545792  avg    124760951708      125009545792 
 *      Particle 179  MH Distance    -84979950721  avg    -84810933155       84979950721 
 *      Particle 180  MH Distance     65004746339  avg     64875477630       65004746339 
 * #### Particle 181  MH Distance         4898909  avg         4894033           4898909 
 *      Particle 182  MH Distance     54996448399  avg     54887074715       54996448399 
 *      Particle 183  MH Distance     24989650959  avg     24939944341       24989650959 
 *
 *  ...
 *  
 *
 *  ...
 *  
 *      Particle 423  MH Distance    -20004698229  avg    -19964920019       20004698229 
 *      Particle 424  MH Distance     -4980054067  avg     -4970130534        4980054067 
 *      Particle 425  MH Distance     14976556280  avg     14946749463       14976556280 
 * #### Particle 426  MH Distance         2699123  avg         2696436           2699123 
 *      Particle 427  MH Distance    124999635710  avg    124751051477      124999635710 
 *      Particle 428  MH Distance     49983399905  avg     49883982589       49983399905 
 *      Particle 429  MH Distance     75003540164  avg     74854385883       75003540164 
 *      Particle 430  MH Distance    -50022486529  avg    -49923030309       50022486529 
 *      Particle 431  MH Distance    -70002890881  avg    -69863680630       70002890881 
 *
 *  ...
 *  
 *      Particle 995  MH Distance    224932345158  avg    224484960223      224932345158 
 *      Particle 996  MH Distance    -54983648841  avg    -54874287893       54983648841 
 *      Particle 997  MH Distance   -189953696643  avg   -189575894148      189953696643 
 *      Particle 998  MH Distance   -154955446759  avg   -154647246205      154955446759 
 *      Particle 999  MH Distance    -84976848362  avg    -84807833881       84976848362 
 * 
 * p=<601,-2217,739>, v=<-17,48,-4>, a=<0,1,-1>
 * 
 * Result Part 1 426
 * 
 * </pre> 
 */
public class Day20_ParticleSwarm
{
  public static void main( String[] args )
  {
    String test_input = "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>;p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>";

    calculatePart01( test_input, 0, true );
    calculate01( getListProd(), 0, true );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, int pBursts, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( ";" ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pBursts, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, int pBursts, boolean pKnzDebug )
  {
    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );

    /*
     * *******************************************************************************************************
     * Creating the list of particles
     * *******************************************************************************************************
     */

    List< Day20Particle > l1 = new ArrayList< Day20Particle >();

    int index_p = 0;

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {

        Day20Particle new_particle = new Day20Particle( index_p, input_str.trim() );

        l1.add( new_particle );

        index_p++;
      }
    }

    /*
     * *******************************************************************************************************
     * Do some tick's
     * *******************************************************************************************************
     */

    for ( int count_ticks = 0; count_ticks < 100_000; count_ticks++ )
    {
      for ( Day20Particle cur_particle : l1 )
      {
        cur_particle.doTick();
      }
    }

    /*
     * *******************************************************************************************************
     * Find the minimum Manhatten-Distance
     * *******************************************************************************************************
     */

    int min_index = 9999;
    long min_mh_distance = Long.MAX_VALUE;

    for ( Day20Particle cur_particle : l1 )
    {
      long cur_mh_dist = Math.abs( cur_particle.getManhattenDistance() );

      if ( cur_mh_dist < min_mh_distance )
      {
        wl( String.format( "#### Particle %3d  MH Distance %15d  avg %15d   %15d ", cur_particle.getIndex(), cur_particle.getManhattenDistance(), cur_particle.getAvgManhattenDistance(), cur_mh_dist ) );

        min_mh_distance = cur_mh_dist;

        min_index = cur_particle.getIndex();
      }
      else
      {
        wl( String.format( "     Particle %3d  MH Distance %15d  avg %15d   %15d ", cur_particle.getIndex(), cur_particle.getManhattenDistance(), cur_particle.getAvgManhattenDistance(), cur_mh_dist ) );
      }
    }

    wl( "" );
    wl( "Minimum Manhatten Distance " + min_index + "  Definition  " + l1.get( min_index ).getInput() );
    wl( "" );
    wl( "Minimum Manhatten Distance " + min_index + "  Cur Values  " + l1.get( min_index ).toString() );
    wl( "" );
    wl( "Result Part 1 " + min_index );
    wl( "" );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day20_input.txt";

    try
    {
      string_array = Files.readAllLines( Path.of( datei_input ) );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }

    return string_array;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}