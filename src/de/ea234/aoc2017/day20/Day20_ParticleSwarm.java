package de.ea234.aoc2017.day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 20: Particle Swarm ---
 * https://adventofcode.com/2017/day/22
 * 
 * https://www.reddit.com/r/adventofcode/comments/7kz6ik/2017_day_20_solutions/
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * #### Particle   0  MH Distance          498497 
 * 
 * Minimum Manhatten Distance 0  Definition  p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
 * 
 * Minimum Manhatten Distance 0  Cur Values   X  p   -499496  v      -999  a        -1    Y  p         0  v         0  a         0   Z  p         0  v         0  a         0 
 * 
 * max_tick_for_simulation 1000
 * 
 * 
 * Result Part 1 0
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * 
 * #### Particle   0  MH Distance        12036890 
 * #### Particle   1  MH Distance         5474978 
 * #### Particle   2  MH Distance         3952345 
 * #### Particle  25  MH Distance         2612750 
 * #### Particle  38  MH Distance         1699402 
 * #### Particle  71  MH Distance         1698357 
 * #### Particle 279  MH Distance          647520 
 * #### Particle 308  MH Distance          628116 
 * 
 * Minimum Manhatten Distance 308  Definition  p=<2978,2082,4280>, v=<-135,-88,-178>, a=<1,0,0>
 * 
 * Minimum Manhatten Distance 308  Cur Values   X  p    369344  v       866  a         1    Y  p    -86006  v       -88  a         0   Z  p   -173898  v      -178  a         0 
 * 
 * max_tick_for_simulation 1000
 * 
 * Result Part 1 308
 *
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * #### Particle   0  MH Distance          498497 
 * 
 * ---------- Tick Nr 0------------------------------------
 * Tick   0   Collision After Tick   ID    0 with ID    1   Key X4Y0Z0
 * 
 * Tick   0   REMOVE ID    0 with key X4Y0Z0
 * Tick   0   REMOVE ID    1 with key X2Y0Z0
 * 
 * Tick   0   List Particles removed   2   remaining   0
 * 
 * 
 * Minimum Manhatten Distance 0  Definition  p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
 * Minimum Manhatten Distance 0  Cur Values   X  p         4  v         1  a        -1    Y  p         0  v         0  a         0   Z  p         0  v         0  a         0 
 * 
 * 
 * max_tick_for_simulation 1 1000
 * max_tick_for_simulation 2 100
 * 
 * Result Part 1 0
 * Result Part 1 0
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * #### Particle   0  MH Distance        12036890 
 * #### Particle   1  MH Distance         5474978 
 * #### Particle   2  MH Distance         3952345 
 * #### Particle  25  MH Distance         2612750 
 * #### Particle  38  MH Distance         1699402 
 * #### Particle  71  MH Distance         1698357 
 * #### Particle 279  MH Distance          647520 
 * #### Particle 308  MH Distance          628116 
 * 
 * ---------- Tick Nr 9------------------------------------
 * Tick   9   Collision After Tick   ID   67 with ID   66   Key X-19Y29Z22
 * Tick   9   Collision After Tick   ID   68 with ID   66   Key X-19Y29Z22
 * Tick   9   Collision After Tick   ID   69 with ID   66   Key X-19Y29Z22
 * Tick   9   Collision After Tick   ID   70 with ID   66   Key X-19Y29Z22
 * Tick   9   Collision After Tick   ID   71 with ID   66   Key X-19Y29Z22
 * Tick   9   Collision After Tick   ID   72 with ID   66   Key X-19Y29Z22
 * Tick   9   Collision After Tick   ID  349 with ID  348   Key X2Y29Z-44
 * Tick   9   Collision After Tick   ID  350 with ID  348   Key X2Y29Z-44
 * Tick   9   Collision After Tick   ID  351 with ID  348   Key X2Y29Z-44
 * Tick   9   Collision After Tick   ID  352 with ID  348   Key X2Y29Z-44
 * Tick   9   Collision After Tick   ID  353 with ID  348   Key X2Y29Z-44
 * 
 * Tick   9   REMOVE ID   67 with key X-19Y29Z22
 * Tick   9   REMOVE ID   66 with key X-19Y29Z22
 * Tick   9   REMOVE ID   68 with key X-19Y29Z22
 * Tick   9   REMOVE ID   69 with key X-19Y29Z22
 * Tick   9   REMOVE ID   70 with key X-19Y29Z22
 * Tick   9   REMOVE ID   71 with key X-19Y29Z22
 * Tick   9   REMOVE ID   72 with key X-19Y29Z22
 * Tick   9   REMOVE ID  349 with key X2Y29Z-44
 * Tick   9   REMOVE ID  348 with key X2Y29Z-44
 * Tick   9   REMOVE ID  350 with key X2Y29Z-44
 * Tick   9   REMOVE ID  351 with key X2Y29Z-44
 * Tick   9   REMOVE ID  352 with key X2Y29Z-44
 * Tick   9   REMOVE ID  353 with key X2Y29Z-44
 * 
 * Tick   9   List Particles removed  13   remaining 987
 * 
 * ---------- Tick Nr 10------------------------------------
 * Tick  10   Collision After Tick   ID  301 with ID  300   Key X12Y-29Z-9
 * Tick  10   Collision After Tick   ID  302 with ID  300   Key X12Y-29Z-9
 * Tick  10   Collision After Tick   ID  341 with ID  340   Key X21Y3Z-26
 * Tick  10   Collision After Tick   ID  342 with ID  340   Key X21Y3Z-26
 * Tick  10   Collision After Tick   ID  343 with ID  340   Key X21Y3Z-26
 * Tick  10   Collision After Tick   ID  344 with ID  340   Key X21Y3Z-26
 * Tick  10   Collision After Tick   ID  345 with ID  340   Key X21Y3Z-26
 * Tick  10   Collision After Tick   ID  346 with ID  340   Key X21Y3Z-26
 * Tick  10   Collision After Tick   ID  347 with ID  340   Key X21Y3Z-26
 * 
 * Tick  10   REMOVE ID  301 with key X12Y-29Z-9
 * Tick  10   REMOVE ID  300 with key X12Y-29Z-9
 * Tick  10   REMOVE ID  302 with key X12Y-29Z-9
 * Tick  10   REMOVE ID  341 with key X21Y3Z-26
 * Tick  10   REMOVE ID  340 with key X21Y3Z-26
 * Tick  10   REMOVE ID  342 with key X21Y3Z-26
 * Tick  10   REMOVE ID  343 with key X21Y3Z-26
 * Tick  10   REMOVE ID  344 with key X21Y3Z-26
 * Tick  10   REMOVE ID  345 with key X21Y3Z-26
 * Tick  10   REMOVE ID  346 with key X21Y3Z-26
 * Tick  10   REMOVE ID  347 with key X21Y3Z-26
 * 
 * Tick  10   List Particles removed  11   remaining 976
 * 
 * ---------- Tick Nr 12------------------------------------
 * Tick  12   Collision After Tick   ID  355 with ID  354   Key X46Y-11Z14
 * Tick  12   Collision After Tick   ID  356 with ID  354   Key X46Y-11Z14
 * Tick  12   Collision After Tick   ID  357 with ID  354   Key X46Y-11Z14
 * Tick  12   Collision After Tick   ID  358 with ID  354   Key X46Y-11Z14
 * Tick  12   Collision After Tick   ID  359 with ID  354   Key X46Y-11Z14
 * Tick  12   Collision After Tick   ID  360 with ID  354   Key X46Y-11Z14
 * 
 * Tick  12   REMOVE ID  355 with key X46Y-11Z14
 * Tick  12   REMOVE ID  354 with key X46Y-11Z14
 * Tick  12   REMOVE ID  356 with key X46Y-11Z14
 * Tick  12   REMOVE ID  357 with key X46Y-11Z14
 * Tick  12   REMOVE ID  358 with key X46Y-11Z14
 * Tick  12   REMOVE ID  359 with key X46Y-11Z14
 * Tick  12   REMOVE ID  360 with key X46Y-11Z14
 * 
 * Tick  12   List Particles removed   7   remaining 969
 * 
 * ---------- Tick Nr 13------------------------------------
 * Tick  13   Collision After Tick   ID   51 with ID   50   Key X27Y24Z24
 * Tick  13   Collision After Tick   ID   52 with ID   50   Key X27Y24Z24
 * Tick  13   Collision After Tick   ID   53 with ID   50   Key X27Y24Z24
 * Tick  13   Collision After Tick   ID   54 with ID   50   Key X27Y24Z24
 * Tick  13   Collision After Tick   ID   55 with ID   50   Key X27Y24Z24
 * Tick  13   Collision After Tick   ID  131 with ID  130   Key X2Y12Z-22
 * Tick  13   Collision After Tick   ID  132 with ID  130   Key X2Y12Z-22
 * Tick  13   Collision After Tick   ID  133 with ID  130   Key X2Y12Z-22
 * Tick  13   Collision After Tick   ID  134 with ID  130   Key X2Y12Z-22
 * Tick  13   Collision After Tick   ID  135 with ID  130   Key X2Y12Z-22
 * Tick  13   Collision After Tick   ID  137 with ID  136   Key X18Y6Z-30
 * 
 * Tick  13   REMOVE ID   51 with key X27Y24Z24
 * Tick  13   REMOVE ID   50 with key X27Y24Z24
 * Tick  13   REMOVE ID   52 with key X27Y24Z24
 * Tick  13   REMOVE ID   53 with key X27Y24Z24
 * Tick  13   REMOVE ID   54 with key X27Y24Z24
 * Tick  13   REMOVE ID   55 with key X27Y24Z24
 * Tick  13   REMOVE ID  131 with key X2Y12Z-22
 * Tick  13   REMOVE ID  130 with key X2Y12Z-22
 * Tick  13   REMOVE ID  132 with key X2Y12Z-22
 * Tick  13   REMOVE ID  133 with key X2Y12Z-22
 * Tick  13   REMOVE ID  134 with key X2Y12Z-22
 * Tick  13   REMOVE ID  135 with key X2Y12Z-22
 * Tick  13   REMOVE ID  137 with key X18Y6Z-30
 * Tick  13   REMOVE ID  136 with key X18Y6Z-30
 * 
 * Tick  13   List Particles removed  14   remaining 955
 * 
 * ---------- Tick Nr 14------------------------------------
 * Tick  14   Collision After Tick   ID  109 with ID  108   Key X-34Y66Z-35
 * Tick  14   Collision After Tick   ID  123 with ID  122   Key X18Y3Z32
 * Tick  14   Collision After Tick   ID  124 with ID  122   Key X18Y3Z32
 * Tick  14   Collision After Tick   ID  125 with ID  122   Key X18Y3Z32
 * Tick  14   Collision After Tick   ID  126 with ID  122   Key X18Y3Z32
 * Tick  14   Collision After Tick   ID  127 with ID  122   Key X18Y3Z32
 * Tick  14   Collision After Tick   ID  128 with ID  122   Key X18Y3Z32
 * Tick  14   Collision After Tick   ID  129 with ID  122   Key X18Y3Z32
 * Tick  14   Collision After Tick   ID  178 with ID  177   Key X14Y34Z-11
 * Tick  14   Collision After Tick   ID  331 with ID  330   Key X39Y-5Z5
 * Tick  14   Collision After Tick   ID  332 with ID  330   Key X39Y-5Z5
 * Tick  14   Collision After Tick   ID  333 with ID  330   Key X39Y-5Z5
 * Tick  14   Collision After Tick   ID  334 with ID  330   Key X39Y-5Z5
 * Tick  14   Collision After Tick   ID  335 with ID  330   Key X39Y-5Z5
 * Tick  14   Collision After Tick   ID  336 with ID  330   Key X39Y-5Z5
 * Tick  14   Collision After Tick   ID  337 with ID  330   Key X39Y-5Z5
 * Tick  14   Collision After Tick   ID  338 with ID  330   Key X39Y-5Z5
 * Tick  14   Collision After Tick   ID  339 with ID  330   Key X39Y-5Z5
 * 
 * Tick  14   REMOVE ID  109 with key X-34Y66Z-35
 * Tick  14   REMOVE ID  108 with key X-34Y66Z-35
 * Tick  14   REMOVE ID  123 with key X18Y3Z32
 * Tick  14   REMOVE ID  122 with key X18Y3Z32
 * Tick  14   REMOVE ID  124 with key X18Y3Z32
 * Tick  14   REMOVE ID  125 with key X18Y3Z32
 * Tick  14   REMOVE ID  126 with key X18Y3Z32
 * Tick  14   REMOVE ID  127 with key X18Y3Z32
 * Tick  14   REMOVE ID  128 with key X18Y3Z32
 * Tick  14   REMOVE ID  129 with key X18Y3Z32
 * Tick  14   REMOVE ID  178 with key X14Y34Z-11
 * Tick  14   REMOVE ID  177 with key X14Y34Z-11
 * Tick  14   REMOVE ID  331 with key X39Y-5Z5
 * Tick  14   REMOVE ID  330 with key X39Y-5Z5
 * Tick  14   REMOVE ID  332 with key X39Y-5Z5
 * Tick  14   REMOVE ID  333 with key X39Y-5Z5
 * Tick  14   REMOVE ID  334 with key X39Y-5Z5
 * Tick  14   REMOVE ID  335 with key X39Y-5Z5
 * Tick  14   REMOVE ID  336 with key X39Y-5Z5
 * Tick  14   REMOVE ID  337 with key X39Y-5Z5
 * Tick  14   REMOVE ID  338 with key X39Y-5Z5
 * Tick  14   REMOVE ID  339 with key X39Y-5Z5
 * 
 * Tick  14   List Particles removed  22   remaining 933
 * 
 * ---------- Tick Nr 15------------------------------------
 * Tick  15   Collision After Tick   ID  385 with ID  384   Key X43Y30Z12
 * Tick  15   Collision After Tick   ID  386 with ID  384   Key X43Y30Z12
 * Tick  15   Collision After Tick   ID  387 with ID  384   Key X43Y30Z12
 * Tick  15   Collision After Tick   ID  388 with ID  384   Key X43Y30Z12
 * Tick  15   Collision After Tick   ID  389 with ID  384   Key X43Y30Z12
 * Tick  15   Collision After Tick   ID  390 with ID  384   Key X43Y30Z12
 * Tick  15   Collision After Tick   ID  391 with ID  384   Key X43Y30Z12
 * Tick  15   Collision After Tick   ID  392 with ID  384   Key X43Y30Z12
 * Tick  15   Collision After Tick   ID  393 with ID  384   Key X43Y30Z12
 * Tick  15   Collision After Tick   ID  453 with ID  452   Key X-29Y28Z7
 * Tick  15   Collision After Tick   ID  454 with ID  452   Key X-29Y28Z7
 * Tick  15   Collision After Tick   ID  464 with ID  463   Key X-17Y27Z-49
 * Tick  15   Collision After Tick   ID  465 with ID  463   Key X-17Y27Z-49
 * Tick  15   Collision After Tick   ID  466 with ID  463   Key X-17Y27Z-49
 * 
 * Tick  15   REMOVE ID  385 with key X43Y30Z12
 * Tick  15   REMOVE ID  384 with key X43Y30Z12
 * Tick  15   REMOVE ID  386 with key X43Y30Z12
 * Tick  15   REMOVE ID  387 with key X43Y30Z12
 * Tick  15   REMOVE ID  388 with key X43Y30Z12
 * Tick  15   REMOVE ID  389 with key X43Y30Z12
 * Tick  15   REMOVE ID  390 with key X43Y30Z12
 * Tick  15   REMOVE ID  391 with key X43Y30Z12
 * Tick  15   REMOVE ID  392 with key X43Y30Z12
 * Tick  15   REMOVE ID  393 with key X43Y30Z12
 * Tick  15   REMOVE ID  453 with key X-29Y28Z7
 * Tick  15   REMOVE ID  452 with key X-29Y28Z7
 * Tick  15   REMOVE ID  454 with key X-29Y28Z7
 * Tick  15   REMOVE ID  464 with key X-17Y27Z-49
 * Tick  15   REMOVE ID  463 with key X-17Y27Z-49
 * Tick  15   REMOVE ID  465 with key X-17Y27Z-49
 * Tick  15   REMOVE ID  466 with key X-17Y27Z-49
 * 
 * Tick  15   List Particles removed  17   remaining 916
 * 
 * ---------- Tick Nr 16------------------------------------
 * Tick  16   Collision After Tick   ID  167 with ID  166   Key X10Y-28Z-24
 * Tick  16   Collision After Tick   ID  168 with ID  166   Key X10Y-28Z-24
 * Tick  16   Collision After Tick   ID  169 with ID  166   Key X10Y-28Z-24
 * Tick  16   Collision After Tick   ID  170 with ID  166   Key X10Y-28Z-24
 * Tick  16   Collision After Tick   ID  171 with ID  166   Key X10Y-28Z-24
 * Tick  16   Collision After Tick   ID  327 with ID  326   Key X-34Y-57Z29
 * Tick  16   Collision After Tick   ID  328 with ID  326   Key X-34Y-57Z29
 * Tick  16   Collision After Tick   ID  329 with ID  326   Key X-34Y-57Z29
 * Tick  16   Collision After Tick   ID  473 with ID  472   Key X-45Y18Z11
 * Tick  16   Collision After Tick   ID  474 with ID  472   Key X-45Y18Z11
 * Tick  16   Collision After Tick   ID  475 with ID  472   Key X-45Y18Z11
 * Tick  16   Collision After Tick   ID  476 with ID  472   Key X-45Y18Z11
 * Tick  16   Collision After Tick   ID  477 with ID  472   Key X-45Y18Z11
 * Tick  16   Collision After Tick   ID  478 with ID  472   Key X-45Y18Z11
 * Tick  16   Collision After Tick   ID  479 with ID  472   Key X-45Y18Z11
 * Tick  16   Collision After Tick   ID  480 with ID  472   Key X-45Y18Z11
 * 
 * Tick  16   REMOVE ID  167 with key X10Y-28Z-24
 * Tick  16   REMOVE ID  166 with key X10Y-28Z-24
 * Tick  16   REMOVE ID  168 with key X10Y-28Z-24
 * Tick  16   REMOVE ID  169 with key X10Y-28Z-24
 * Tick  16   REMOVE ID  170 with key X10Y-28Z-24
 * Tick  16   REMOVE ID  171 with key X10Y-28Z-24
 * Tick  16   REMOVE ID  327 with key X-34Y-57Z29
 * Tick  16   REMOVE ID  326 with key X-34Y-57Z29
 * Tick  16   REMOVE ID  328 with key X-34Y-57Z29
 * Tick  16   REMOVE ID  329 with key X-34Y-57Z29
 * Tick  16   REMOVE ID  473 with key X-45Y18Z11
 * Tick  16   REMOVE ID  472 with key X-45Y18Z11
 * Tick  16   REMOVE ID  474 with key X-45Y18Z11
 * Tick  16   REMOVE ID  475 with key X-45Y18Z11
 * Tick  16   REMOVE ID  476 with key X-45Y18Z11
 * Tick  16   REMOVE ID  477 with key X-45Y18Z11
 * Tick  16   REMOVE ID  478 with key X-45Y18Z11
 * Tick  16   REMOVE ID  479 with key X-45Y18Z11
 * Tick  16   REMOVE ID  480 with key X-45Y18Z11
 * 
 * Tick  16   List Particles removed  19   remaining 897
 * 
 * ---------- Tick Nr 17------------------------------------
 * Tick  17   Collision After Tick   ID   23 with ID   22   Key X-27Y0Z4
 * Tick  17   Collision After Tick   ID   24 with ID   22   Key X-27Y0Z4
 * Tick  17   Collision After Tick   ID   34 with ID   33   Key X-18Y19Z-50
 * Tick  17   Collision After Tick   ID   35 with ID   33   Key X-18Y19Z-50
 * Tick  17   Collision After Tick   ID   36 with ID   33   Key X-18Y19Z-50
 * Tick  17   Collision After Tick   ID   37 with ID   33   Key X-18Y19Z-50
 * Tick  17   Collision After Tick   ID   38 with ID   33   Key X-18Y19Z-50
 * Tick  17   Collision After Tick   ID  287 with ID  286   Key X-11Y14Z-31
 * Tick  17   Collision After Tick   ID  288 with ID  286   Key X-11Y14Z-31
 * 
 * Tick  17   REMOVE ID   23 with key X-27Y0Z4
 * Tick  17   REMOVE ID   22 with key X-27Y0Z4
 * Tick  17   REMOVE ID   24 with key X-27Y0Z4
 * Tick  17   REMOVE ID   34 with key X-18Y19Z-50
 * Tick  17   REMOVE ID   33 with key X-18Y19Z-50
 * Tick  17   REMOVE ID   35 with key X-18Y19Z-50
 * Tick  17   REMOVE ID   36 with key X-18Y19Z-50
 * Tick  17   REMOVE ID   37 with key X-18Y19Z-50
 * Tick  17   REMOVE ID   38 with key X-18Y19Z-50
 * Tick  17   REMOVE ID  287 with key X-11Y14Z-31
 * Tick  17   REMOVE ID  286 with key X-11Y14Z-31
 * Tick  17   REMOVE ID  288 with key X-11Y14Z-31
 * 
 * Tick  17   List Particles removed  12   remaining 885
 * 
 * ---------- Tick Nr 18------------------------------------
 * Tick  18   Collision After Tick   ID  194 with ID  193   Key X-62Y-18Z0
 * Tick  18   Collision After Tick   ID  195 with ID  193   Key X-62Y-18Z0
 * Tick  18   Collision After Tick   ID  205 with ID  204   Key X-2Y-9Z8
 * Tick  18   Collision After Tick   ID  206 with ID  204   Key X-2Y-9Z8
 * Tick  18   Collision After Tick   ID  207 with ID  204   Key X-2Y-9Z8
 * Tick  18   Collision After Tick   ID  208 with ID  204   Key X-2Y-9Z8
 * Tick  18   Collision After Tick   ID  209 with ID  204   Key X-2Y-9Z8
 * Tick  18   Collision After Tick   ID  210 with ID  204   Key X-2Y-9Z8
 * Tick  18   Collision After Tick   ID  211 with ID  204   Key X-2Y-9Z8
 * Tick  18   Collision After Tick   ID  266 with ID  265   Key X-9Y-44Z53
 * Tick  18   Collision After Tick   ID  267 with ID  265   Key X-9Y-44Z53
 * Tick  18   Collision After Tick   ID  268 with ID  265   Key X-9Y-44Z53
 * Tick  18   Collision After Tick   ID  277 with ID  276   Key X18Y16Z-58
 * Tick  18   Collision After Tick   ID  278 with ID  276   Key X18Y16Z-58
 * Tick  18   Collision After Tick   ID  279 with ID  276   Key X18Y16Z-58
 * Tick  18   Collision After Tick   ID  280 with ID  276   Key X18Y16Z-58
 * Tick  18   Collision After Tick   ID  456 with ID  455   Key X16Y-31Z35
 * Tick  18   Collision After Tick   ID  457 with ID  455   Key X16Y-31Z35
 * Tick  18   Collision After Tick   ID  458 with ID  455   Key X16Y-31Z35
 * Tick  18   Collision After Tick   ID  459 with ID  455   Key X16Y-31Z35
 * Tick  18   Collision After Tick   ID  460 with ID  455   Key X16Y-31Z35
 * Tick  18   Collision After Tick   ID  461 with ID  455   Key X16Y-31Z35
 * Tick  18   Collision After Tick   ID  462 with ID  455   Key X16Y-31Z35
 * Tick  18   Collision After Tick   ID  468 with ID  467   Key X-12Y-57Z7
 * Tick  18   Collision After Tick   ID  469 with ID  467   Key X-12Y-57Z7
 * Tick  18   Collision After Tick   ID  470 with ID  467   Key X-12Y-57Z7
 * Tick  18   Collision After Tick   ID  471 with ID  467   Key X-12Y-57Z7
 * Tick  18   Collision After Tick   ID  488 with ID  487   Key X7Y27Z26
 * Tick  18   Collision After Tick   ID  489 with ID  487   Key X7Y27Z26
 * 
 * Tick  18   REMOVE ID  194 with key X-62Y-18Z0
 * Tick  18   REMOVE ID  193 with key X-62Y-18Z0
 * Tick  18   REMOVE ID  195 with key X-62Y-18Z0
 * Tick  18   REMOVE ID  205 with key X-2Y-9Z8
 * Tick  18   REMOVE ID  204 with key X-2Y-9Z8
 * Tick  18   REMOVE ID  206 with key X-2Y-9Z8
 * Tick  18   REMOVE ID  207 with key X-2Y-9Z8
 * Tick  18   REMOVE ID  208 with key X-2Y-9Z8
 * Tick  18   REMOVE ID  209 with key X-2Y-9Z8
 * Tick  18   REMOVE ID  210 with key X-2Y-9Z8
 * Tick  18   REMOVE ID  211 with key X-2Y-9Z8
 * Tick  18   REMOVE ID  266 with key X-9Y-44Z53
 * Tick  18   REMOVE ID  265 with key X-9Y-44Z53
 * Tick  18   REMOVE ID  267 with key X-9Y-44Z53
 * Tick  18   REMOVE ID  268 with key X-9Y-44Z53
 * Tick  18   REMOVE ID  277 with key X18Y16Z-58
 * Tick  18   REMOVE ID  276 with key X18Y16Z-58
 * Tick  18   REMOVE ID  278 with key X18Y16Z-58
 * Tick  18   REMOVE ID  279 with key X18Y16Z-58
 * Tick  18   REMOVE ID  280 with key X18Y16Z-58
 * Tick  18   REMOVE ID  456 with key X16Y-31Z35
 * Tick  18   REMOVE ID  455 with key X16Y-31Z35
 * Tick  18   REMOVE ID  457 with key X16Y-31Z35
 * Tick  18   REMOVE ID  458 with key X16Y-31Z35
 * Tick  18   REMOVE ID  459 with key X16Y-31Z35
 * Tick  18   REMOVE ID  460 with key X16Y-31Z35
 * Tick  18   REMOVE ID  461 with key X16Y-31Z35
 * Tick  18   REMOVE ID  462 with key X16Y-31Z35
 * Tick  18   REMOVE ID  468 with key X-12Y-57Z7
 * Tick  18   REMOVE ID  467 with key X-12Y-57Z7
 * Tick  18   REMOVE ID  469 with key X-12Y-57Z7
 * Tick  18   REMOVE ID  470 with key X-12Y-57Z7
 * Tick  18   REMOVE ID  471 with key X-12Y-57Z7
 * Tick  18   REMOVE ID  488 with key X7Y27Z26
 * Tick  18   REMOVE ID  487 with key X7Y27Z26
 * Tick  18   REMOVE ID  489 with key X7Y27Z26
 * 
 * Tick  18   List Particles removed  36   remaining 849
 * 
 * ---------- Tick Nr 19------------------------------------
 * Tick  19   Collision After Tick   ID  111 with ID  110   Key X-69Y-29Z-5
 * Tick  19   Collision After Tick   ID  112 with ID  110   Key X-69Y-29Z-5
 * Tick  19   Collision After Tick   ID  113 with ID  110   Key X-69Y-29Z-5
 * Tick  19   Collision After Tick   ID  114 with ID  110   Key X-69Y-29Z-5
 * Tick  19   Collision After Tick   ID  115 with ID  110   Key X-69Y-29Z-5
 * Tick  19   Collision After Tick   ID  116 with ID  110   Key X-69Y-29Z-5
 * Tick  19   Collision After Tick   ID  117 with ID  110   Key X-69Y-29Z-5
 * Tick  19   Collision After Tick   ID  118 with ID  110   Key X-69Y-29Z-5
 * Tick  19   Collision After Tick   ID  180 with ID  179   Key X-23Y-38Z-50
 * Tick  19   Collision After Tick   ID  181 with ID  179   Key X-23Y-38Z-50
 * Tick  19   Collision After Tick   ID  182 with ID  179   Key X-23Y-38Z-50
 * Tick  19   Collision After Tick   ID  183 with ID  179   Key X-23Y-38Z-50
 * Tick  19   Collision After Tick   ID  184 with ID  179   Key X-23Y-38Z-50
 * Tick  19   Collision After Tick   ID  231 with ID  230   Key X24Y21Z-47
 * Tick  19   Collision After Tick   ID  232 with ID  230   Key X24Y21Z-47
 * Tick  19   Collision After Tick   ID  233 with ID  230   Key X24Y21Z-47
 * Tick  19   Collision After Tick   ID  234 with ID  230   Key X24Y21Z-47
 * Tick  19   Collision After Tick   ID  235 with ID  230   Key X24Y21Z-47
 * Tick  19   Collision After Tick   ID  236 with ID  230   Key X24Y21Z-47
 * Tick  19   Collision After Tick   ID  237 with ID  230   Key X24Y21Z-47
 * Tick  19   Collision After Tick   ID  238 with ID  230   Key X24Y21Z-47
 * 
 * Tick  19   REMOVE ID  111 with key X-69Y-29Z-5
 * Tick  19   REMOVE ID  110 with key X-69Y-29Z-5
 * Tick  19   REMOVE ID  112 with key X-69Y-29Z-5
 * Tick  19   REMOVE ID  113 with key X-69Y-29Z-5
 * Tick  19   REMOVE ID  114 with key X-69Y-29Z-5
 * Tick  19   REMOVE ID  115 with key X-69Y-29Z-5
 * Tick  19   REMOVE ID  116 with key X-69Y-29Z-5
 * Tick  19   REMOVE ID  117 with key X-69Y-29Z-5
 * Tick  19   REMOVE ID  118 with key X-69Y-29Z-5
 * Tick  19   REMOVE ID  180 with key X-23Y-38Z-50
 * Tick  19   REMOVE ID  179 with key X-23Y-38Z-50
 * Tick  19   REMOVE ID  181 with key X-23Y-38Z-50
 * Tick  19   REMOVE ID  182 with key X-23Y-38Z-50
 * Tick  19   REMOVE ID  183 with key X-23Y-38Z-50
 * Tick  19   REMOVE ID  184 with key X-23Y-38Z-50
 * Tick  19   REMOVE ID  231 with key X24Y21Z-47
 * Tick  19   REMOVE ID  230 with key X24Y21Z-47
 * Tick  19   REMOVE ID  232 with key X24Y21Z-47
 * Tick  19   REMOVE ID  233 with key X24Y21Z-47
 * Tick  19   REMOVE ID  234 with key X24Y21Z-47
 * Tick  19   REMOVE ID  235 with key X24Y21Z-47
 * Tick  19   REMOVE ID  236 with key X24Y21Z-47
 * Tick  19   REMOVE ID  237 with key X24Y21Z-47
 * Tick  19   REMOVE ID  238 with key X24Y21Z-47
 * 
 * Tick  19   List Particles removed  24   remaining 825
 * 
 * ---------- Tick Nr 20------------------------------------
 * Tick  20   Collision After Tick   ID    9 with ID    8   Key X-41Y-12Z5
 * Tick  20   Collision After Tick   ID   10 with ID    8   Key X-41Y-12Z5
 * Tick  20   Collision After Tick   ID   11 with ID    8   Key X-41Y-12Z5
 * Tick  20   Collision After Tick   ID   12 with ID    8   Key X-41Y-12Z5
 * Tick  20   Collision After Tick   ID   57 with ID   56   Key X18Y-50Z-11
 * Tick  20   Collision After Tick   ID   58 with ID   56   Key X18Y-50Z-11
 * Tick  20   Collision After Tick   ID   59 with ID   56   Key X18Y-50Z-11
 * Tick  20   Collision After Tick   ID   60 with ID   56   Key X18Y-50Z-11
 * Tick  20   Collision After Tick   ID   61 with ID   56   Key X18Y-50Z-11
 * Tick  20   Collision After Tick   ID   62 with ID   56   Key X18Y-50Z-11
 * Tick  20   Collision After Tick   ID   63 with ID   56   Key X18Y-50Z-11
 * Tick  20   Collision After Tick   ID   64 with ID   56   Key X18Y-50Z-11
 * Tick  20   Collision After Tick   ID   65 with ID   56   Key X18Y-50Z-11
 * Tick  20   Collision After Tick   ID  154 with ID  153   Key X-24Y-26Z25
 * Tick  20   Collision After Tick   ID  155 with ID  153   Key X-24Y-26Z25
 * Tick  20   Collision After Tick   ID  156 with ID  153   Key X-24Y-26Z25
 * Tick  20   Collision After Tick   ID  157 with ID  153   Key X-24Y-26Z25
 * Tick  20   Collision After Tick   ID  158 with ID  153   Key X-24Y-26Z25
 * Tick  20   Collision After Tick   ID  159 with ID  153   Key X-24Y-26Z25
 * Tick  20   Collision After Tick   ID  160 with ID  153   Key X-24Y-26Z25
 * Tick  20   Collision After Tick   ID  161 with ID  153   Key X-24Y-26Z25
 * Tick  20   Collision After Tick   ID  282 with ID  281   Key X30Y-34Z-13
 * Tick  20   Collision After Tick   ID  283 with ID  281   Key X30Y-34Z-13
 * Tick  20   Collision After Tick   ID  284 with ID  281   Key X30Y-34Z-13
 * Tick  20   Collision After Tick   ID  285 with ID  281   Key X30Y-34Z-13
 * Tick  20   Collision After Tick   ID  417 with ID  416   Key X-65Y-21Z-12
 * Tick  20   Collision After Tick   ID  418 with ID  416   Key X-65Y-21Z-12
 * Tick  20   Collision After Tick   ID  419 with ID  416   Key X-65Y-21Z-12
 * Tick  20   Collision After Tick   ID  420 with ID  416   Key X-65Y-21Z-12
 * Tick  20   Collision After Tick   ID  421 with ID  416   Key X-65Y-21Z-12
 * Tick  20   Collision After Tick   ID  422 with ID  416   Key X-65Y-21Z-12
 * Tick  20   Collision After Tick   ID  423 with ID  416   Key X-65Y-21Z-12
 * Tick  20   Collision After Tick   ID  424 with ID  416   Key X-65Y-21Z-12
 * 
 * Tick  20   REMOVE ID    9 with key X-41Y-12Z5
 * Tick  20   REMOVE ID    8 with key X-41Y-12Z5
 * Tick  20   REMOVE ID   10 with key X-41Y-12Z5
 * Tick  20   REMOVE ID   11 with key X-41Y-12Z5
 * Tick  20   REMOVE ID   12 with key X-41Y-12Z5
 * Tick  20   REMOVE ID   57 with key X18Y-50Z-11
 * Tick  20   REMOVE ID   56 with key X18Y-50Z-11
 * Tick  20   REMOVE ID   58 with key X18Y-50Z-11
 * Tick  20   REMOVE ID   59 with key X18Y-50Z-11
 * Tick  20   REMOVE ID   60 with key X18Y-50Z-11
 * Tick  20   REMOVE ID   61 with key X18Y-50Z-11
 * Tick  20   REMOVE ID   62 with key X18Y-50Z-11
 * Tick  20   REMOVE ID   63 with key X18Y-50Z-11
 * Tick  20   REMOVE ID   64 with key X18Y-50Z-11
 * Tick  20   REMOVE ID   65 with key X18Y-50Z-11
 * Tick  20   REMOVE ID  154 with key X-24Y-26Z25
 * Tick  20   REMOVE ID  153 with key X-24Y-26Z25
 * Tick  20   REMOVE ID  155 with key X-24Y-26Z25
 * Tick  20   REMOVE ID  156 with key X-24Y-26Z25
 * Tick  20   REMOVE ID  157 with key X-24Y-26Z25
 * Tick  20   REMOVE ID  158 with key X-24Y-26Z25
 * Tick  20   REMOVE ID  159 with key X-24Y-26Z25
 * Tick  20   REMOVE ID  160 with key X-24Y-26Z25
 * Tick  20   REMOVE ID  161 with key X-24Y-26Z25
 * Tick  20   REMOVE ID  282 with key X30Y-34Z-13
 * Tick  20   REMOVE ID  281 with key X30Y-34Z-13
 * Tick  20   REMOVE ID  283 with key X30Y-34Z-13
 * Tick  20   REMOVE ID  284 with key X30Y-34Z-13
 * Tick  20   REMOVE ID  285 with key X30Y-34Z-13
 * Tick  20   REMOVE ID  417 with key X-65Y-21Z-12
 * Tick  20   REMOVE ID  416 with key X-65Y-21Z-12
 * Tick  20   REMOVE ID  418 with key X-65Y-21Z-12
 * Tick  20   REMOVE ID  419 with key X-65Y-21Z-12
 * Tick  20   REMOVE ID  420 with key X-65Y-21Z-12
 * Tick  20   REMOVE ID  421 with key X-65Y-21Z-12
 * Tick  20   REMOVE ID  422 with key X-65Y-21Z-12
 * Tick  20   REMOVE ID  423 with key X-65Y-21Z-12
 * Tick  20   REMOVE ID  424 with key X-65Y-21Z-12
 * 
 * Tick  20   List Particles removed  38   remaining 787
 * 
 * ---------- Tick Nr 22------------------------------------
 * Tick  22   Collision After Tick   ID   47 with ID   46   Key X-2Y-10Z16
 * Tick  22   Collision After Tick   ID   48 with ID   46   Key X-2Y-10Z16
 * Tick  22   Collision After Tick   ID   49 with ID   46   Key X-2Y-10Z16
 * Tick  22   Collision After Tick   ID  376 with ID  375   Key X21Y44Z-3
 * Tick  22   Collision After Tick   ID  377 with ID  375   Key X21Y44Z-3
 * Tick  22   Collision After Tick   ID  378 with ID  375   Key X21Y44Z-3
 * Tick  22   Collision After Tick   ID  379 with ID  375   Key X21Y44Z-3
 * Tick  22   Collision After Tick   ID  447 with ID  446   Key X-22Y4Z-6
 * Tick  22   Collision After Tick   ID  448 with ID  446   Key X-22Y4Z-6
 * Tick  22   Collision After Tick   ID  449 with ID  446   Key X-22Y4Z-6
 * Tick  22   Collision After Tick   ID  450 with ID  446   Key X-22Y4Z-6
 * Tick  22   Collision After Tick   ID  451 with ID  446   Key X-22Y4Z-6
 * 
 * Tick  22   REMOVE ID   47 with key X-2Y-10Z16
 * Tick  22   REMOVE ID   46 with key X-2Y-10Z16
 * Tick  22   REMOVE ID   48 with key X-2Y-10Z16
 * Tick  22   REMOVE ID   49 with key X-2Y-10Z16
 * Tick  22   REMOVE ID  376 with key X21Y44Z-3
 * Tick  22   REMOVE ID  375 with key X21Y44Z-3
 * Tick  22   REMOVE ID  377 with key X21Y44Z-3
 * Tick  22   REMOVE ID  378 with key X21Y44Z-3
 * Tick  22   REMOVE ID  379 with key X21Y44Z-3
 * Tick  22   REMOVE ID  447 with key X-22Y4Z-6
 * Tick  22   REMOVE ID  446 with key X-22Y4Z-6
 * Tick  22   REMOVE ID  448 with key X-22Y4Z-6
 * Tick  22   REMOVE ID  449 with key X-22Y4Z-6
 * Tick  22   REMOVE ID  450 with key X-22Y4Z-6
 * Tick  22   REMOVE ID  451 with key X-22Y4Z-6
 * 
 * Tick  22   List Particles removed  15   remaining 772
 * 
 * ---------- Tick Nr 23------------------------------------
 * Tick  23   Collision After Tick   ID  213 with ID  212   Key X-17Y-20Z4
 * Tick  23   Collision After Tick   ID  214 with ID  212   Key X-17Y-20Z4
 * Tick  23   Collision After Tick   ID  304 with ID  303   Key X38Y-30Z8
 * Tick  23   Collision After Tick   ID  305 with ID  303   Key X38Y-30Z8
 * Tick  23   Collision After Tick   ID  306 with ID  303   Key X38Y-30Z8
 * Tick  23   Collision After Tick   ID  307 with ID  303   Key X38Y-30Z8
 * Tick  23   Collision After Tick   ID  308 with ID  303   Key X38Y-30Z8
 * Tick  23   Collision After Tick   ID  309 with ID  303   Key X38Y-30Z8
 * Tick  23   Collision After Tick   ID  399 with ID  398   Key X-27Y14Z21
 * Tick  23   Collision After Tick   ID  400 with ID  398   Key X-27Y14Z21
 * Tick  23   Collision After Tick   ID  401 with ID  398   Key X-27Y14Z21
 * Tick  23   Collision After Tick   ID  402 with ID  398   Key X-27Y14Z21
 * Tick  23   Collision After Tick   ID  403 with ID  398   Key X-27Y14Z21
 * Tick  23   Collision After Tick   ID  404 with ID  398   Key X-27Y14Z21
 * 
 * Tick  23   REMOVE ID  213 with key X-17Y-20Z4
 * Tick  23   REMOVE ID  212 with key X-17Y-20Z4
 * Tick  23   REMOVE ID  214 with key X-17Y-20Z4
 * Tick  23   REMOVE ID  304 with key X38Y-30Z8
 * Tick  23   REMOVE ID  303 with key X38Y-30Z8
 * Tick  23   REMOVE ID  305 with key X38Y-30Z8
 * Tick  23   REMOVE ID  306 with key X38Y-30Z8
 * Tick  23   REMOVE ID  307 with key X38Y-30Z8
 * Tick  23   REMOVE ID  308 with key X38Y-30Z8
 * Tick  23   REMOVE ID  309 with key X38Y-30Z8
 * Tick  23   REMOVE ID  399 with key X-27Y14Z21
 * Tick  23   REMOVE ID  398 with key X-27Y14Z21
 * Tick  23   REMOVE ID  400 with key X-27Y14Z21
 * Tick  23   REMOVE ID  401 with key X-27Y14Z21
 * Tick  23   REMOVE ID  402 with key X-27Y14Z21
 * Tick  23   REMOVE ID  403 with key X-27Y14Z21
 * Tick  23   REMOVE ID  404 with key X-27Y14Z21
 * 
 * Tick  23   List Particles removed  17   remaining 755
 * 
 * ---------- Tick Nr 24------------------------------------
 * Tick  24   Collision After Tick   ID   74 with ID   73   Key X21Y-53Z-19
 * Tick  24   Collision After Tick   ID   75 with ID   73   Key X21Y-53Z-19
 * Tick  24   Collision After Tick   ID   76 with ID   73   Key X21Y-53Z-19
 * Tick  24   Collision After Tick   ID   77 with ID   73   Key X21Y-53Z-19
 * Tick  24   Collision After Tick   ID   78 with ID   73   Key X21Y-53Z-19
 * Tick  24   Collision After Tick   ID   79 with ID   73   Key X21Y-53Z-19
 * Tick  24   Collision After Tick   ID   80 with ID   73   Key X21Y-53Z-19
 * Tick  24   Collision After Tick   ID   81 with ID   73   Key X21Y-53Z-19
 * Tick  24   Collision After Tick   ID   82 with ID   73   Key X21Y-53Z-19
 * Tick  24   Collision After Tick   ID   98 with ID   97   Key X6Y47Z4
 * Tick  24   Collision After Tick   ID   99 with ID   97   Key X6Y47Z4
 * Tick  24   Collision After Tick   ID  100 with ID   97   Key X6Y47Z4
 * Tick  24   Collision After Tick   ID  249 with ID  248   Key X-8Y7Z-11
 * Tick  24   Collision After Tick   ID  250 with ID  248   Key X-8Y7Z-11
 * Tick  24   Collision After Tick   ID  251 with ID  248   Key X-8Y7Z-11
 * Tick  24   Collision After Tick   ID  252 with ID  248   Key X-8Y7Z-11
 * Tick  24   Collision After Tick   ID  253 with ID  248   Key X-8Y7Z-11
 * Tick  24   Collision After Tick   ID  254 with ID  248   Key X-8Y7Z-11
 * Tick  24   Collision After Tick   ID  296 with ID  295   Key X-10Y-40Z-32
 * Tick  24   Collision After Tick   ID  297 with ID  295   Key X-10Y-40Z-32
 * Tick  24   Collision After Tick   ID  298 with ID  295   Key X-10Y-40Z-32
 * Tick  24   Collision After Tick   ID  299 with ID  295   Key X-10Y-40Z-32
 * 
 * Tick  24   REMOVE ID   74 with key X21Y-53Z-19
 * Tick  24   REMOVE ID   73 with key X21Y-53Z-19
 * Tick  24   REMOVE ID   75 with key X21Y-53Z-19
 * Tick  24   REMOVE ID   76 with key X21Y-53Z-19
 * Tick  24   REMOVE ID   77 with key X21Y-53Z-19
 * Tick  24   REMOVE ID   78 with key X21Y-53Z-19
 * Tick  24   REMOVE ID   79 with key X21Y-53Z-19
 * Tick  24   REMOVE ID   80 with key X21Y-53Z-19
 * Tick  24   REMOVE ID   81 with key X21Y-53Z-19
 * Tick  24   REMOVE ID   82 with key X21Y-53Z-19
 * Tick  24   REMOVE ID   98 with key X6Y47Z4
 * Tick  24   REMOVE ID   97 with key X6Y47Z4
 * Tick  24   REMOVE ID   99 with key X6Y47Z4
 * Tick  24   REMOVE ID  100 with key X6Y47Z4
 * Tick  24   REMOVE ID  249 with key X-8Y7Z-11
 * Tick  24   REMOVE ID  248 with key X-8Y7Z-11
 * Tick  24   REMOVE ID  250 with key X-8Y7Z-11
 * Tick  24   REMOVE ID  251 with key X-8Y7Z-11
 * Tick  24   REMOVE ID  252 with key X-8Y7Z-11
 * Tick  24   REMOVE ID  253 with key X-8Y7Z-11
 * Tick  24   REMOVE ID  254 with key X-8Y7Z-11
 * Tick  24   REMOVE ID  296 with key X-10Y-40Z-32
 * Tick  24   REMOVE ID  295 with key X-10Y-40Z-32
 * Tick  24   REMOVE ID  297 with key X-10Y-40Z-32
 * Tick  24   REMOVE ID  298 with key X-10Y-40Z-32
 * Tick  24   REMOVE ID  299 with key X-10Y-40Z-32
 * 
 * Tick  24   List Particles removed  26   remaining 729
 * 
 * ---------- Tick Nr 25------------------------------------
 * Tick  25   Collision After Tick   ID  163 with ID  162   Key X6Y2Z6
 * Tick  25   Collision After Tick   ID  164 with ID  162   Key X6Y2Z6
 * Tick  25   Collision After Tick   ID  165 with ID  162   Key X6Y2Z6
 * Tick  25   Collision After Tick   ID  197 with ID  196   Key X38Y-29Z9
 * Tick  25   Collision After Tick   ID  198 with ID  196   Key X38Y-29Z9
 * Tick  25   Collision After Tick   ID  199 with ID  196   Key X38Y-29Z9
 * Tick  25   Collision After Tick   ID  200 with ID  196   Key X38Y-29Z9
 * Tick  25   Collision After Tick   ID  201 with ID  196   Key X38Y-29Z9
 * Tick  25   Collision After Tick   ID  202 with ID  196   Key X38Y-29Z9
 * Tick  25   Collision After Tick   ID  203 with ID  196   Key X38Y-29Z9
 * Tick  25   Collision After Tick   ID  311 with ID  310   Key X-16Y30Z2
 * Tick  25   Collision After Tick   ID  312 with ID  310   Key X-16Y30Z2
 * Tick  25   Collision After Tick   ID  313 with ID  310   Key X-16Y30Z2
 * Tick  25   Collision After Tick   ID  314 with ID  310   Key X-16Y30Z2
 * Tick  25   Collision After Tick   ID  315 with ID  310   Key X-16Y30Z2
 * Tick  25   Collision After Tick   ID  316 with ID  310   Key X-16Y30Z2
 * 
 * Tick  25   REMOVE ID  163 with key X6Y2Z6
 * Tick  25   REMOVE ID  162 with key X6Y2Z6
 * Tick  25   REMOVE ID  164 with key X6Y2Z6
 * Tick  25   REMOVE ID  165 with key X6Y2Z6
 * Tick  25   REMOVE ID  197 with key X38Y-29Z9
 * Tick  25   REMOVE ID  196 with key X38Y-29Z9
 * Tick  25   REMOVE ID  198 with key X38Y-29Z9
 * Tick  25   REMOVE ID  199 with key X38Y-29Z9
 * Tick  25   REMOVE ID  200 with key X38Y-29Z9
 * Tick  25   REMOVE ID  201 with key X38Y-29Z9
 * Tick  25   REMOVE ID  202 with key X38Y-29Z9
 * Tick  25   REMOVE ID  203 with key X38Y-29Z9
 * Tick  25   REMOVE ID  311 with key X-16Y30Z2
 * Tick  25   REMOVE ID  310 with key X-16Y30Z2
 * Tick  25   REMOVE ID  312 with key X-16Y30Z2
 * Tick  25   REMOVE ID  313 with key X-16Y30Z2
 * Tick  25   REMOVE ID  314 with key X-16Y30Z2
 * Tick  25   REMOVE ID  315 with key X-16Y30Z2
 * Tick  25   REMOVE ID  316 with key X-16Y30Z2
 * 
 * Tick  25   List Particles removed  19   remaining 710
 * 
 * ---------- Tick Nr 26------------------------------------
 * Tick  26   Collision After Tick   ID    1 with ID    0   Key X-37Y29Z27
 * Tick  26   Collision After Tick   ID    2 with ID    0   Key X-37Y29Z27
 * Tick  26   Collision After Tick   ID    3 with ID    0   Key X-37Y29Z27
 * Tick  26   Collision After Tick   ID    4 with ID    0   Key X-37Y29Z27
 * Tick  26   Collision After Tick   ID    5 with ID    0   Key X-37Y29Z27
 * Tick  26   Collision After Tick   ID    6 with ID    0   Key X-37Y29Z27
 * Tick  26   Collision After Tick   ID    7 with ID    0   Key X-37Y29Z27
 * 
 * Tick  26   REMOVE ID    1 with key X-37Y29Z27
 * Tick  26   REMOVE ID    0 with key X-37Y29Z27
 * Tick  26   REMOVE ID    2 with key X-37Y29Z27
 * Tick  26   REMOVE ID    3 with key X-37Y29Z27
 * Tick  26   REMOVE ID    4 with key X-37Y29Z27
 * Tick  26   REMOVE ID    5 with key X-37Y29Z27
 * Tick  26   REMOVE ID    6 with key X-37Y29Z27
 * Tick  26   REMOVE ID    7 with key X-37Y29Z27
 * 
 * Tick  26   List Particles removed   8   remaining 702
 * 
 * ---------- Tick Nr 27------------------------------------
 * Tick  27   Collision After Tick   ID  491 with ID  490   Key X42Y3Z-16
 * Tick  27   Collision After Tick   ID  492 with ID  490   Key X42Y3Z-16
 * Tick  27   Collision After Tick   ID  493 with ID  490   Key X42Y3Z-16
 * Tick  27   Collision After Tick   ID  494 with ID  490   Key X42Y3Z-16
 * Tick  27   Collision After Tick   ID  495 with ID  490   Key X42Y3Z-16
 * 
 * Tick  27   REMOVE ID  491 with key X42Y3Z-16
 * Tick  27   REMOVE ID  490 with key X42Y3Z-16
 * Tick  27   REMOVE ID  492 with key X42Y3Z-16
 * Tick  27   REMOVE ID  493 with key X42Y3Z-16
 * Tick  27   REMOVE ID  494 with key X42Y3Z-16
 * Tick  27   REMOVE ID  495 with key X42Y3Z-16
 * 
 * Tick  27   List Particles removed   6   remaining 696
 * 
 * ---------- Tick Nr 28------------------------------------
 * Tick  28   Collision After Tick   ID  147 with ID  146   Key X-33Y-13Z-30
 * Tick  28   Collision After Tick   ID  148 with ID  146   Key X-33Y-13Z-30
 * Tick  28   Collision After Tick   ID  149 with ID  146   Key X-33Y-13Z-30
 * Tick  28   Collision After Tick   ID  150 with ID  146   Key X-33Y-13Z-30
 * Tick  28   Collision After Tick   ID  151 with ID  146   Key X-33Y-13Z-30
 * Tick  28   Collision After Tick   ID  152 with ID  146   Key X-33Y-13Z-30
 * Tick  28   Collision After Tick   ID  290 with ID  289   Key X20Y-1Z50
 * Tick  28   Collision After Tick   ID  291 with ID  289   Key X20Y-1Z50
 * Tick  28   Collision After Tick   ID  292 with ID  289   Key X20Y-1Z50
 * Tick  28   Collision After Tick   ID  293 with ID  289   Key X20Y-1Z50
 * Tick  28   Collision After Tick   ID  294 with ID  289   Key X20Y-1Z50
 * Tick  28   Collision After Tick   ID  406 with ID  405   Key X-20Y-10Z-33
 * Tick  28   Collision After Tick   ID  407 with ID  405   Key X-20Y-10Z-33
 * Tick  28   Collision After Tick   ID  409 with ID  408   Key X21Y-51Z31
 * Tick  28   Collision After Tick   ID  410 with ID  408   Key X21Y-51Z31
 * Tick  28   Collision After Tick   ID  411 with ID  408   Key X21Y-51Z31
 * Tick  28   Collision After Tick   ID  412 with ID  408   Key X21Y-51Z31
 * Tick  28   Collision After Tick   ID  413 with ID  408   Key X21Y-51Z31
 * Tick  28   Collision After Tick   ID  414 with ID  408   Key X21Y-51Z31
 * Tick  28   Collision After Tick   ID  415 with ID  408   Key X21Y-51Z31
 * 
 * Tick  28   REMOVE ID  147 with key X-33Y-13Z-30
 * Tick  28   REMOVE ID  146 with key X-33Y-13Z-30
 * Tick  28   REMOVE ID  148 with key X-33Y-13Z-30
 * Tick  28   REMOVE ID  149 with key X-33Y-13Z-30
 * Tick  28   REMOVE ID  150 with key X-33Y-13Z-30
 * Tick  28   REMOVE ID  151 with key X-33Y-13Z-30
 * Tick  28   REMOVE ID  152 with key X-33Y-13Z-30
 * Tick  28   REMOVE ID  290 with key X20Y-1Z50
 * Tick  28   REMOVE ID  289 with key X20Y-1Z50
 * Tick  28   REMOVE ID  291 with key X20Y-1Z50
 * Tick  28   REMOVE ID  292 with key X20Y-1Z50
 * Tick  28   REMOVE ID  293 with key X20Y-1Z50
 * Tick  28   REMOVE ID  294 with key X20Y-1Z50
 * Tick  28   REMOVE ID  406 with key X-20Y-10Z-33
 * Tick  28   REMOVE ID  405 with key X-20Y-10Z-33
 * Tick  28   REMOVE ID  407 with key X-20Y-10Z-33
 * Tick  28   REMOVE ID  409 with key X21Y-51Z31
 * Tick  28   REMOVE ID  408 with key X21Y-51Z31
 * Tick  28   REMOVE ID  410 with key X21Y-51Z31
 * Tick  28   REMOVE ID  411 with key X21Y-51Z31
 * Tick  28   REMOVE ID  412 with key X21Y-51Z31
 * Tick  28   REMOVE ID  413 with key X21Y-51Z31
 * Tick  28   REMOVE ID  414 with key X21Y-51Z31
 * Tick  28   REMOVE ID  415 with key X21Y-51Z31
 * 
 * Tick  28   List Particles removed  24   remaining 672
 * 
 * ---------- Tick Nr 29------------------------------------
 * Tick  29   Collision After Tick   ID  256 with ID  255   Key X-16Y-22Z-4
 * Tick  29   Collision After Tick   ID  257 with ID  255   Key X-16Y-22Z-4
 * Tick  29   Collision After Tick   ID  258 with ID  255   Key X-16Y-22Z-4
 * Tick  29   Collision After Tick   ID  259 with ID  255   Key X-16Y-22Z-4
 * Tick  29   Collision After Tick   ID  260 with ID  255   Key X-16Y-22Z-4
 * Tick  29   Collision After Tick   ID  261 with ID  255   Key X-16Y-22Z-4
 * Tick  29   Collision After Tick   ID  262 with ID  255   Key X-16Y-22Z-4
 * Tick  29   Collision After Tick   ID  263 with ID  255   Key X-16Y-22Z-4
 * Tick  29   Collision After Tick   ID  264 with ID  255   Key X-16Y-22Z-4
 * Tick  29   Collision After Tick   ID  435 with ID  434   Key X-40Y7Z-70
 * Tick  29   Collision After Tick   ID  436 with ID  434   Key X-40Y7Z-70
 * Tick  29   Collision After Tick   ID  437 with ID  434   Key X-40Y7Z-70
 * Tick  29   Collision After Tick   ID  438 with ID  434   Key X-40Y7Z-70
 * Tick  29   Collision After Tick   ID  439 with ID  434   Key X-40Y7Z-70
 * Tick  29   Collision After Tick   ID  440 with ID  434   Key X-40Y7Z-70
 * Tick  29   Collision After Tick   ID  441 with ID  434   Key X-40Y7Z-70
 * Tick  29   Collision After Tick   ID  442 with ID  434   Key X-40Y7Z-70
 * Tick  29   Collision After Tick   ID  443 with ID  434   Key X-40Y7Z-70
 * 
 * Tick  29   REMOVE ID  256 with key X-16Y-22Z-4
 * Tick  29   REMOVE ID  255 with key X-16Y-22Z-4
 * Tick  29   REMOVE ID  257 with key X-16Y-22Z-4
 * Tick  29   REMOVE ID  258 with key X-16Y-22Z-4
 * Tick  29   REMOVE ID  259 with key X-16Y-22Z-4
 * Tick  29   REMOVE ID  260 with key X-16Y-22Z-4
 * Tick  29   REMOVE ID  261 with key X-16Y-22Z-4
 * Tick  29   REMOVE ID  262 with key X-16Y-22Z-4
 * Tick  29   REMOVE ID  263 with key X-16Y-22Z-4
 * Tick  29   REMOVE ID  264 with key X-16Y-22Z-4
 * Tick  29   REMOVE ID  435 with key X-40Y7Z-70
 * Tick  29   REMOVE ID  434 with key X-40Y7Z-70
 * Tick  29   REMOVE ID  436 with key X-40Y7Z-70
 * Tick  29   REMOVE ID  437 with key X-40Y7Z-70
 * Tick  29   REMOVE ID  438 with key X-40Y7Z-70
 * Tick  29   REMOVE ID  439 with key X-40Y7Z-70
 * Tick  29   REMOVE ID  440 with key X-40Y7Z-70
 * Tick  29   REMOVE ID  441 with key X-40Y7Z-70
 * Tick  29   REMOVE ID  442 with key X-40Y7Z-70
 * Tick  29   REMOVE ID  443 with key X-40Y7Z-70
 * 
 * Tick  29   List Particles removed  20   remaining 652
 * 
 * ---------- Tick Nr 30------------------------------------
 * Tick  30   Collision After Tick   ID  139 with ID  138   Key X-20Y-1Z-7
 * Tick  30   Collision After Tick   ID  140 with ID  138   Key X-20Y-1Z-7
 * Tick  30   Collision After Tick   ID  141 with ID  138   Key X-20Y-1Z-7
 * Tick  30   Collision After Tick   ID  142 with ID  138   Key X-20Y-1Z-7
 * Tick  30   Collision After Tick   ID  143 with ID  138   Key X-20Y-1Z-7
 * Tick  30   Collision After Tick   ID  144 with ID  138   Key X-20Y-1Z-7
 * Tick  30   Collision After Tick   ID  145 with ID  138   Key X-20Y-1Z-7
 * Tick  30   Collision After Tick   ID  186 with ID  185   Key X20Y60Z0
 * Tick  30   Collision After Tick   ID  187 with ID  185   Key X20Y60Z0
 * Tick  30   Collision After Tick   ID  188 with ID  185   Key X20Y60Z0
 * Tick  30   Collision After Tick   ID  189 with ID  185   Key X20Y60Z0
 * Tick  30   Collision After Tick   ID  190 with ID  185   Key X20Y60Z0
 * Tick  30   Collision After Tick   ID  191 with ID  185   Key X20Y60Z0
 * Tick  30   Collision After Tick   ID  192 with ID  185   Key X20Y60Z0
 * Tick  30   Collision After Tick   ID  369 with ID  368   Key X-41Y22Z-35
 * Tick  30   Collision After Tick   ID  370 with ID  368   Key X-41Y22Z-35
 * Tick  30   Collision After Tick   ID  371 with ID  368   Key X-41Y22Z-35
 * Tick  30   Collision After Tick   ID  372 with ID  368   Key X-41Y22Z-35
 * Tick  30   Collision After Tick   ID  373 with ID  368   Key X-41Y22Z-35
 * Tick  30   Collision After Tick   ID  374 with ID  368   Key X-41Y22Z-35
 * 
 * Tick  30   REMOVE ID  139 with key X-20Y-1Z-7
 * Tick  30   REMOVE ID  138 with key X-20Y-1Z-7
 * Tick  30   REMOVE ID  140 with key X-20Y-1Z-7
 * Tick  30   REMOVE ID  141 with key X-20Y-1Z-7
 * Tick  30   REMOVE ID  142 with key X-20Y-1Z-7
 * Tick  30   REMOVE ID  143 with key X-20Y-1Z-7
 * Tick  30   REMOVE ID  144 with key X-20Y-1Z-7
 * Tick  30   REMOVE ID  145 with key X-20Y-1Z-7
 * Tick  30   REMOVE ID  186 with key X20Y60Z0
 * Tick  30   REMOVE ID  185 with key X20Y60Z0
 * Tick  30   REMOVE ID  187 with key X20Y60Z0
 * Tick  30   REMOVE ID  188 with key X20Y60Z0
 * Tick  30   REMOVE ID  189 with key X20Y60Z0
 * Tick  30   REMOVE ID  190 with key X20Y60Z0
 * Tick  30   REMOVE ID  191 with key X20Y60Z0
 * Tick  30   REMOVE ID  192 with key X20Y60Z0
 * Tick  30   REMOVE ID  369 with key X-41Y22Z-35
 * Tick  30   REMOVE ID  368 with key X-41Y22Z-35
 * Tick  30   REMOVE ID  370 with key X-41Y22Z-35
 * Tick  30   REMOVE ID  371 with key X-41Y22Z-35
 * Tick  30   REMOVE ID  372 with key X-41Y22Z-35
 * Tick  30   REMOVE ID  373 with key X-41Y22Z-35
 * Tick  30   REMOVE ID  374 with key X-41Y22Z-35
 * 
 * Tick  30   List Particles removed  23   remaining 629
 * 
 * ---------- Tick Nr 31------------------------------------
 * Tick  31   Collision After Tick   ID  216 with ID  215   Key X16Y-28Z29
 * Tick  31   Collision After Tick   ID  217 with ID  215   Key X16Y-28Z29
 * Tick  31   Collision After Tick   ID  218 with ID  215   Key X16Y-28Z29
 * Tick  31   Collision After Tick   ID  219 with ID  215   Key X16Y-28Z29
 * Tick  31   Collision After Tick   ID  220 with ID  215   Key X16Y-28Z29
 * Tick  31   Collision After Tick   ID  221 with ID  215   Key X16Y-28Z29
 * Tick  31   Collision After Tick   ID  240 with ID  239   Key X-17Y22Z22
 * Tick  31   Collision After Tick   ID  241 with ID  239   Key X-17Y22Z22
 * Tick  31   Collision After Tick   ID  242 with ID  239   Key X-17Y22Z22
 * Tick  31   Collision After Tick   ID  243 with ID  239   Key X-17Y22Z22
 * Tick  31   Collision After Tick   ID  244 with ID  239   Key X-17Y22Z22
 * Tick  31   Collision After Tick   ID  245 with ID  239   Key X-17Y22Z22
 * Tick  31   Collision After Tick   ID  246 with ID  239   Key X-17Y22Z22
 * Tick  31   Collision After Tick   ID  247 with ID  239   Key X-17Y22Z22
 * 
 * Tick  31   REMOVE ID  216 with key X16Y-28Z29
 * Tick  31   REMOVE ID  215 with key X16Y-28Z29
 * Tick  31   REMOVE ID  217 with key X16Y-28Z29
 * Tick  31   REMOVE ID  218 with key X16Y-28Z29
 * Tick  31   REMOVE ID  219 with key X16Y-28Z29
 * Tick  31   REMOVE ID  220 with key X16Y-28Z29
 * Tick  31   REMOVE ID  221 with key X16Y-28Z29
 * Tick  31   REMOVE ID  240 with key X-17Y22Z22
 * Tick  31   REMOVE ID  239 with key X-17Y22Z22
 * Tick  31   REMOVE ID  241 with key X-17Y22Z22
 * Tick  31   REMOVE ID  242 with key X-17Y22Z22
 * Tick  31   REMOVE ID  243 with key X-17Y22Z22
 * Tick  31   REMOVE ID  244 with key X-17Y22Z22
 * Tick  31   REMOVE ID  245 with key X-17Y22Z22
 * Tick  31   REMOVE ID  246 with key X-17Y22Z22
 * Tick  31   REMOVE ID  247 with key X-17Y22Z22
 * 
 * Tick  31   List Particles removed  16   remaining 613
 * 
 * ---------- Tick Nr 32------------------------------------
 * Tick  32   Collision After Tick   ID   26 with ID   25   Key X18Y19Z-47
 * Tick  32   Collision After Tick   ID   27 with ID   25   Key X18Y19Z-47
 * Tick  32   Collision After Tick   ID   28 with ID   25   Key X18Y19Z-47
 * Tick  32   Collision After Tick   ID   29 with ID   25   Key X18Y19Z-47
 * Tick  32   Collision After Tick   ID   30 with ID   25   Key X18Y19Z-47
 * Tick  32   Collision After Tick   ID   31 with ID   25   Key X18Y19Z-47
 * Tick  32   Collision After Tick   ID   32 with ID   25   Key X18Y19Z-47
 * Tick  32   Collision After Tick   ID   40 with ID   39   Key X32Y-21Z-36
 * Tick  32   Collision After Tick   ID   41 with ID   39   Key X32Y-21Z-36
 * Tick  32   Collision After Tick   ID   42 with ID   39   Key X32Y-21Z-36
 * Tick  32   Collision After Tick   ID   43 with ID   39   Key X32Y-21Z-36
 * Tick  32   Collision After Tick   ID   44 with ID   39   Key X32Y-21Z-36
 * Tick  32   Collision After Tick   ID   45 with ID   39   Key X32Y-21Z-36
 * Tick  32   Collision After Tick   ID   94 with ID   93   Key X-61Y58Z8
 * Tick  32   Collision After Tick   ID   95 with ID   93   Key X-61Y58Z8
 * Tick  32   Collision After Tick   ID   96 with ID   93   Key X-61Y58Z8
 * Tick  32   Collision After Tick   ID  102 with ID  101   Key X39Y19Z23
 * Tick  32   Collision After Tick   ID  103 with ID  101   Key X39Y19Z23
 * Tick  32   Collision After Tick   ID  104 with ID  101   Key X39Y19Z23
 * Tick  32   Collision After Tick   ID  105 with ID  101   Key X39Y19Z23
 * Tick  32   Collision After Tick   ID  106 with ID  101   Key X39Y19Z23
 * Tick  32   Collision After Tick   ID  107 with ID  101   Key X39Y19Z23
 * Tick  32   Collision After Tick   ID  270 with ID  269   Key X15Y-52Z-27
 * Tick  32   Collision After Tick   ID  271 with ID  269   Key X15Y-52Z-27
 * Tick  32   Collision After Tick   ID  272 with ID  269   Key X15Y-52Z-27
 * Tick  32   Collision After Tick   ID  273 with ID  269   Key X15Y-52Z-27
 * Tick  32   Collision After Tick   ID  274 with ID  269   Key X15Y-52Z-27
 * Tick  32   Collision After Tick   ID  275 with ID  269   Key X15Y-52Z-27
 * Tick  32   Collision After Tick   ID  362 with ID  361   Key X-22Y-21Z-38
 * Tick  32   Collision After Tick   ID  363 with ID  361   Key X-22Y-21Z-38
 * Tick  32   Collision After Tick   ID  364 with ID  361   Key X-22Y-21Z-38
 * Tick  32   Collision After Tick   ID  365 with ID  361   Key X-22Y-21Z-38
 * Tick  32   Collision After Tick   ID  366 with ID  361   Key X-22Y-21Z-38
 * Tick  32   Collision After Tick   ID  367 with ID  361   Key X-22Y-21Z-38
 * Tick  32   Collision After Tick   ID  395 with ID  394   Key X-27Y22Z-12
 * Tick  32   Collision After Tick   ID  396 with ID  394   Key X-27Y22Z-12
 * Tick  32   Collision After Tick   ID  397 with ID  394   Key X-27Y22Z-12
 * 
 * Tick  32   REMOVE ID   26 with key X18Y19Z-47
 * Tick  32   REMOVE ID   25 with key X18Y19Z-47
 * Tick  32   REMOVE ID   27 with key X18Y19Z-47
 * Tick  32   REMOVE ID   28 with key X18Y19Z-47
 * Tick  32   REMOVE ID   29 with key X18Y19Z-47
 * Tick  32   REMOVE ID   30 with key X18Y19Z-47
 * Tick  32   REMOVE ID   31 with key X18Y19Z-47
 * Tick  32   REMOVE ID   32 with key X18Y19Z-47
 * Tick  32   REMOVE ID   40 with key X32Y-21Z-36
 * Tick  32   REMOVE ID   39 with key X32Y-21Z-36
 * Tick  32   REMOVE ID   41 with key X32Y-21Z-36
 * Tick  32   REMOVE ID   42 with key X32Y-21Z-36
 * Tick  32   REMOVE ID   43 with key X32Y-21Z-36
 * Tick  32   REMOVE ID   44 with key X32Y-21Z-36
 * Tick  32   REMOVE ID   45 with key X32Y-21Z-36
 * Tick  32   REMOVE ID   94 with key X-61Y58Z8
 * Tick  32   REMOVE ID   93 with key X-61Y58Z8
 * Tick  32   REMOVE ID   95 with key X-61Y58Z8
 * Tick  32   REMOVE ID   96 with key X-61Y58Z8
 * Tick  32   REMOVE ID  102 with key X39Y19Z23
 * Tick  32   REMOVE ID  101 with key X39Y19Z23
 * Tick  32   REMOVE ID  103 with key X39Y19Z23
 * Tick  32   REMOVE ID  104 with key X39Y19Z23
 * Tick  32   REMOVE ID  105 with key X39Y19Z23
 * Tick  32   REMOVE ID  106 with key X39Y19Z23
 * Tick  32   REMOVE ID  107 with key X39Y19Z23
 * Tick  32   REMOVE ID  270 with key X15Y-52Z-27
 * Tick  32   REMOVE ID  269 with key X15Y-52Z-27
 * Tick  32   REMOVE ID  271 with key X15Y-52Z-27
 * Tick  32   REMOVE ID  272 with key X15Y-52Z-27
 * Tick  32   REMOVE ID  273 with key X15Y-52Z-27
 * Tick  32   REMOVE ID  274 with key X15Y-52Z-27
 * Tick  32   REMOVE ID  275 with key X15Y-52Z-27
 * Tick  32   REMOVE ID  362 with key X-22Y-21Z-38
 * Tick  32   REMOVE ID  361 with key X-22Y-21Z-38
 * Tick  32   REMOVE ID  363 with key X-22Y-21Z-38
 * Tick  32   REMOVE ID  364 with key X-22Y-21Z-38
 * Tick  32   REMOVE ID  365 with key X-22Y-21Z-38
 * Tick  32   REMOVE ID  366 with key X-22Y-21Z-38
 * Tick  32   REMOVE ID  367 with key X-22Y-21Z-38
 * Tick  32   REMOVE ID  395 with key X-27Y22Z-12
 * Tick  32   REMOVE ID  394 with key X-27Y22Z-12
 * Tick  32   REMOVE ID  396 with key X-27Y22Z-12
 * Tick  32   REMOVE ID  397 with key X-27Y22Z-12
 * 
 * Tick  32   List Particles removed  44   remaining 569
 * 
 * ---------- Tick Nr 33------------------------------------
 * Tick  33   Collision After Tick   ID  426 with ID  425   Key X23Y10Z8
 * Tick  33   Collision After Tick   ID  427 with ID  425   Key X23Y10Z8
 * Tick  33   Collision After Tick   ID  428 with ID  425   Key X23Y10Z8
 * Tick  33   Collision After Tick   ID  429 with ID  425   Key X23Y10Z8
 * Tick  33   Collision After Tick   ID  430 with ID  425   Key X23Y10Z8
 * Tick  33   Collision After Tick   ID  431 with ID  425   Key X23Y10Z8
 * Tick  33   Collision After Tick   ID  432 with ID  425   Key X23Y10Z8
 * Tick  33   Collision After Tick   ID  433 with ID  425   Key X23Y10Z8
 * 
 * Tick  33   REMOVE ID  426 with key X23Y10Z8
 * Tick  33   REMOVE ID  425 with key X23Y10Z8
 * Tick  33   REMOVE ID  427 with key X23Y10Z8
 * Tick  33   REMOVE ID  428 with key X23Y10Z8
 * Tick  33   REMOVE ID  429 with key X23Y10Z8
 * Tick  33   REMOVE ID  430 with key X23Y10Z8
 * Tick  33   REMOVE ID  431 with key X23Y10Z8
 * Tick  33   REMOVE ID  432 with key X23Y10Z8
 * Tick  33   REMOVE ID  433 with key X23Y10Z8
 * 
 * Tick  33   List Particles removed   9   remaining 560
 * 
 * ---------- Tick Nr 34------------------------------------
 * Tick  34   Collision After Tick   ID  173 with ID  172   Key X-47Y0Z28
 * Tick  34   Collision After Tick   ID  174 with ID  172   Key X-47Y0Z28
 * Tick  34   Collision After Tick   ID  175 with ID  172   Key X-47Y0Z28
 * Tick  34   Collision After Tick   ID  176 with ID  172   Key X-47Y0Z28
 * Tick  34   Collision After Tick   ID  482 with ID  481   Key X-28Y20Z-30
 * Tick  34   Collision After Tick   ID  483 with ID  481   Key X-28Y20Z-30
 * Tick  34   Collision After Tick   ID  484 with ID  481   Key X-28Y20Z-30
 * Tick  34   Collision After Tick   ID  485 with ID  481   Key X-28Y20Z-30
 * Tick  34   Collision After Tick   ID  486 with ID  481   Key X-28Y20Z-30
 * 
 * Tick  34   REMOVE ID  173 with key X-47Y0Z28
 * Tick  34   REMOVE ID  172 with key X-47Y0Z28
 * Tick  34   REMOVE ID  174 with key X-47Y0Z28
 * Tick  34   REMOVE ID  175 with key X-47Y0Z28
 * Tick  34   REMOVE ID  176 with key X-47Y0Z28
 * Tick  34   REMOVE ID  482 with key X-28Y20Z-30
 * Tick  34   REMOVE ID  481 with key X-28Y20Z-30
 * Tick  34   REMOVE ID  483 with key X-28Y20Z-30
 * Tick  34   REMOVE ID  484 with key X-28Y20Z-30
 * Tick  34   REMOVE ID  485 with key X-28Y20Z-30
 * Tick  34   REMOVE ID  486 with key X-28Y20Z-30
 * 
 * Tick  34   List Particles removed  11   remaining 549
 * 
 * ---------- Tick Nr 35------------------------------------
 * Tick  35   Collision After Tick   ID  381 with ID  380   Key X30Y41Z28
 * Tick  35   Collision After Tick   ID  382 with ID  380   Key X30Y41Z28
 * Tick  35   Collision After Tick   ID  383 with ID  380   Key X30Y41Z28
 * 
 * Tick  35   REMOVE ID  381 with key X30Y41Z28
 * Tick  35   REMOVE ID  380 with key X30Y41Z28
 * Tick  35   REMOVE ID  382 with key X30Y41Z28
 * Tick  35   REMOVE ID  383 with key X30Y41Z28
 * 
 * Tick  35   List Particles removed   4   remaining 545
 * 
 * ---------- Tick Nr 36------------------------------------
 * Tick  36   Collision After Tick   ID  120 with ID  119   Key X-16Y-15Z18
 * Tick  36   Collision After Tick   ID  121 with ID  119   Key X-16Y-15Z18
 * 
 * Tick  36   REMOVE ID  120 with key X-16Y-15Z18
 * Tick  36   REMOVE ID  119 with key X-16Y-15Z18
 * Tick  36   REMOVE ID  121 with key X-16Y-15Z18
 * 
 * Tick  36   List Particles removed   3   remaining 542
 * 
 * ---------- Tick Nr 37------------------------------------
 * Tick  37   Collision After Tick   ID   14 with ID   13   Key X9Y14Z10
 * Tick  37   Collision After Tick   ID   15 with ID   13   Key X9Y14Z10
 * Tick  37   Collision After Tick   ID   16 with ID   13   Key X9Y14Z10
 * Tick  37   Collision After Tick   ID   17 with ID   13   Key X9Y14Z10
 * Tick  37   Collision After Tick   ID   18 with ID   13   Key X9Y14Z10
 * Tick  37   Collision After Tick   ID   19 with ID   13   Key X9Y14Z10
 * Tick  37   Collision After Tick   ID   20 with ID   13   Key X9Y14Z10
 * Tick  37   Collision After Tick   ID   21 with ID   13   Key X9Y14Z10
 * Tick  37   Collision After Tick   ID  223 with ID  222   Key X-5Y48Z-7
 * Tick  37   Collision After Tick   ID  224 with ID  222   Key X-5Y48Z-7
 * Tick  37   Collision After Tick   ID  225 with ID  222   Key X-5Y48Z-7
 * Tick  37   Collision After Tick   ID  226 with ID  222   Key X-5Y48Z-7
 * Tick  37   Collision After Tick   ID  227 with ID  222   Key X-5Y48Z-7
 * Tick  37   Collision After Tick   ID  228 with ID  222   Key X-5Y48Z-7
 * Tick  37   Collision After Tick   ID  229 with ID  222   Key X-5Y48Z-7
 * 
 * Tick  37   REMOVE ID   14 with key X9Y14Z10
 * Tick  37   REMOVE ID   13 with key X9Y14Z10
 * Tick  37   REMOVE ID   15 with key X9Y14Z10
 * Tick  37   REMOVE ID   16 with key X9Y14Z10
 * Tick  37   REMOVE ID   17 with key X9Y14Z10
 * Tick  37   REMOVE ID   18 with key X9Y14Z10
 * Tick  37   REMOVE ID   19 with key X9Y14Z10
 * Tick  37   REMOVE ID   20 with key X9Y14Z10
 * Tick  37   REMOVE ID   21 with key X9Y14Z10
 * Tick  37   REMOVE ID  223 with key X-5Y48Z-7
 * Tick  37   REMOVE ID  222 with key X-5Y48Z-7
 * Tick  37   REMOVE ID  224 with key X-5Y48Z-7
 * Tick  37   REMOVE ID  225 with key X-5Y48Z-7
 * Tick  37   REMOVE ID  226 with key X-5Y48Z-7
 * Tick  37   REMOVE ID  227 with key X-5Y48Z-7
 * Tick  37   REMOVE ID  228 with key X-5Y48Z-7
 * Tick  37   REMOVE ID  229 with key X-5Y48Z-7
 * 
 * Tick  37   List Particles removed  17   remaining 525
 * 
 * ---------- Tick Nr 38------------------------------------
 * Tick  38   Collision After Tick   ID   84 with ID   83   Key X58Y-23Z-18
 * Tick  38   Collision After Tick   ID   85 with ID   83   Key X58Y-23Z-18
 * Tick  38   Collision After Tick   ID   86 with ID   83   Key X58Y-23Z-18
 * Tick  38   Collision After Tick   ID   87 with ID   83   Key X58Y-23Z-18
 * Tick  38   Collision After Tick   ID   88 with ID   83   Key X58Y-23Z-18
 * Tick  38   Collision After Tick   ID   89 with ID   83   Key X58Y-23Z-18
 * Tick  38   Collision After Tick   ID   90 with ID   83   Key X58Y-23Z-18
 * Tick  38   Collision After Tick   ID   91 with ID   83   Key X58Y-23Z-18
 * Tick  38   Collision After Tick   ID   92 with ID   83   Key X58Y-23Z-18
 * Tick  38   Collision After Tick   ID  318 with ID  317   Key X6Y1Z-5
 * Tick  38   Collision After Tick   ID  319 with ID  317   Key X6Y1Z-5
 * Tick  38   Collision After Tick   ID  320 with ID  317   Key X6Y1Z-5
 * Tick  38   Collision After Tick   ID  321 with ID  317   Key X6Y1Z-5
 * Tick  38   Collision After Tick   ID  322 with ID  317   Key X6Y1Z-5
 * Tick  38   Collision After Tick   ID  323 with ID  317   Key X6Y1Z-5
 * Tick  38   Collision After Tick   ID  324 with ID  317   Key X6Y1Z-5
 * Tick  38   Collision After Tick   ID  325 with ID  317   Key X6Y1Z-5
 * Tick  38   Collision After Tick   ID  445 with ID  444   Key X-33Y82Z-43
 * 
 * Tick  38   REMOVE ID   84 with key X58Y-23Z-18
 * Tick  38   REMOVE ID   83 with key X58Y-23Z-18
 * Tick  38   REMOVE ID   85 with key X58Y-23Z-18
 * Tick  38   REMOVE ID   86 with key X58Y-23Z-18
 * Tick  38   REMOVE ID   87 with key X58Y-23Z-18
 * Tick  38   REMOVE ID   88 with key X58Y-23Z-18
 * Tick  38   REMOVE ID   89 with key X58Y-23Z-18
 * Tick  38   REMOVE ID   90 with key X58Y-23Z-18
 * Tick  38   REMOVE ID   91 with key X58Y-23Z-18
 * Tick  38   REMOVE ID   92 with key X58Y-23Z-18
 * Tick  38   REMOVE ID  318 with key X6Y1Z-5
 * Tick  38   REMOVE ID  317 with key X6Y1Z-5
 * Tick  38   REMOVE ID  319 with key X6Y1Z-5
 * Tick  38   REMOVE ID  320 with key X6Y1Z-5
 * Tick  38   REMOVE ID  321 with key X6Y1Z-5
 * Tick  38   REMOVE ID  322 with key X6Y1Z-5
 * Tick  38   REMOVE ID  323 with key X6Y1Z-5
 * Tick  38   REMOVE ID  324 with key X6Y1Z-5
 * Tick  38   REMOVE ID  325 with key X6Y1Z-5
 * Tick  38   REMOVE ID  445 with key X-33Y82Z-43
 * Tick  38   REMOVE ID  444 with key X-33Y82Z-43
 * 
 * Tick  38   List Particles removed  21   remaining 504
 * 
 * 
 * Minimum Manhatten Distance 308  Definition  p=<2978,2082,4280>, v=<-135,-88,-178>, a=<1,0,0>
 * Minimum Manhatten Distance 308  Cur Values   X  p        38  v      -111  a         1    Y  p       -30  v       -88  a         0   Z  p         8  v      -178  a         0 
 * 
 * 
 * max_tick_for_simulation 1 1000
 * max_tick_for_simulation 2 100
 * 
 * Result Part 1 308
 * Result Part 1 504
 * 
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

    List< Day20Particle > list_particles = new ArrayList< Day20Particle >();

    int particle_index = 0;

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {
        list_particles.add( new Day20Particle( particle_index, input_str.trim() ) );

        particle_index++;
      }
    }

    /*
     * *******************************************************************************************************
     * Do some tick's
     * *******************************************************************************************************
     */

    int max_tick_for_simulation_1 = 1000;

    for ( int count_ticks = 0; count_ticks < max_tick_for_simulation_1; count_ticks++ )
    {
      for ( Day20Particle cur_particle : list_particles )
      {
        cur_particle.doTick();
      }
    }

    /*
     * *******************************************************************************************************
     * Find the minimum Manhatten-Distance
     * *******************************************************************************************************
     */

    wl( "" );

    int min_index = 9999;

    long min_mh_distance = Long.MAX_VALUE;

    for ( Day20Particle cur_particle : list_particles )
    {
      if ( cur_particle.getManhattenDistance() < min_mh_distance )
      {
        wl( String.format( "#### Particle %3d  MH Distance %15d ", cur_particle.getID(), cur_particle.getManhattenDistance() ) );

        min_mh_distance = cur_particle.getManhattenDistance();

        min_index = cur_particle.getID();
      }
      else
      {
        //wl( String.format( "     Particle %3d  MH Distance %15d ", cur_particle.getIndex(), cur_particle.getManhattenDistance() ) );
      }
    }

    Day20Particle res_1_particle = list_particles.get( min_index );

    /*
     * *******************************************************************************************************
     * Reset for part 2
     * *******************************************************************************************************
     */

    Properties x_prop = new Properties();

    for ( Day20Particle cur_particle : list_particles )
    {
      cur_particle.reset();

      String collision_before_tick = x_prop.getProperty( cur_particle.getKey() );

      if ( ( collision_before_tick != null ) && ( cur_particle.isNotMyID( collision_before_tick ) ) )
      {
        wl( "Debug - A - Collision before " + cur_particle.getKey() + " with " + collision_before_tick );
      }

      x_prop.setProperty( cur_particle.getKey(), "" + cur_particle.getID() );
    }

    /*
     * *******************************************************************************************************
     * Do some tick's
     * *******************************************************************************************************
     */

    int max_tick_for_simulation_2 = 100;

    String dbg_str = "";

    for ( int count_ticks = 0; count_ticks < max_tick_for_simulation_2; count_ticks++ )
    {
      dbg_str = "\n---------- Tick Nr " + count_ticks + "------------------------------------\n";

      List< Day20Particle > deletion_list = new ArrayList< Day20Particle >();

      for ( Day20Particle cur_particle : list_particles )
      {
        String collision_before_tick = x_prop.getProperty( cur_particle.getKey() );

        if ( ( collision_before_tick != null ) && ( cur_particle.isNotMyID( collision_before_tick ) ) )
        {
          wl( dbg_str + String.format( "Tick %3d   Collision Before Tick  ID %4d with ID %4s   Key %s", count_ticks, cur_particle.getID(), collision_before_tick, cur_particle.getKey() ) );
        }
        else
        {
          x_prop.remove( cur_particle.getKey() );

          cur_particle.doTick();

          String collision_after_tick = x_prop.getProperty( cur_particle.getKey() );

          if ( ( collision_after_tick != null ) && ( cur_particle.isNotMyID( collision_after_tick ) ) )
          {
            wl( dbg_str + String.format( "Tick %3d   Collision After Tick   ID %4d with ID %4s   Key %s", count_ticks, cur_particle.getID(), collision_after_tick, cur_particle.getKey() ) );

            dbg_str = "";

            if ( deletion_list.contains( cur_particle ) == false )
            {
              deletion_list.add( cur_particle );
            }

            int index_particle_b = Integer.valueOf( collision_after_tick ).intValue();

            Day20Particle particle_b = getParticle( list_particles, index_particle_b );

            if ( deletion_list.contains( particle_b ) == false )
            {
              deletion_list.add( particle_b );
            }
          }
          else
          {
            x_prop.setProperty( cur_particle.getKey(), "" + cur_particle.getID() );
          }
        }
      }

      if ( deletion_list.size() > 0 )
      {
        wl( "" );

        for ( Day20Particle cur_particle : deletion_list )
        {
          wl( String.format( "Tick %3d   REMOVE ID %4d with key %s", count_ticks, cur_particle.getID(), cur_particle.getKey() ) );

          x_prop.remove( cur_particle.getKey() );

          list_particles.remove( cur_particle );
        }

        wl( "" );
        wl( String.format( "Tick %3d   List Particles removed %3d   remaining %3d", count_ticks, deletion_list.size(), list_particles.size() ) );
      }
    }

    wl( "" );
    wl( "" );
    wl( "Minimum Manhatten Distance " + min_index + "  Definition  " + res_1_particle.getInput() );
    wl( "Minimum Manhatten Distance " + min_index + "  Cur Values  " + res_1_particle.toString() );
    wl( "" );
    wl( "" );
    wl( "max_tick_for_simulation 1 " + max_tick_for_simulation_1 );
    wl( "max_tick_for_simulation 2 " + max_tick_for_simulation_2 );
    wl( "" );
    wl( "Result Part 1 " + min_index );
    wl( "Result Part 1 " + list_particles.size() );
    wl( "" );
  }

  private static Day20Particle getParticle( List< Day20Particle > pList, long pIndex )
  {
    for ( Day20Particle cur_particle : pList )
    {
      if ( cur_particle.isMyID( pIndex ) )
      {
        return cur_particle;
      }
    }

    return null;
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
