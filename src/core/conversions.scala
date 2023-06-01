/*
    Quantify, version [unreleased]. Copyright 2023 Jon Pretty, Propensive OÜ.

    The primary distribution site is: https://propensive.com/

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
    file except in compliance with the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the
    License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    either express or implied. See the License for the specific language governing permissions
    and limitations under the License.
*/

package quantify

import rudiments.*

import scala.compiletime.*

import language.experimental.captureChecking

erased trait Ratio[UnitsType <: Measure, RatioType <: Double & Singleton]

// Units of length

trait Inches[Power <: Nat] extends Units[Power, Length]
object Inches:
  erased given inchesPerMetre: Ratio[Inches[1] & Metres[-1], 39.3701] = erasedValue

trait Feet[Power <: Nat] extends Units[Power, Length]
object Feet:
  erased given feetPerMetre: Ratio[Feet[1] & Metres[-1], 3.28084] = erasedValue

trait Yards[Power <: Nat] extends Units[Power, Length]
object Yards:
  erased given yardsPerMetre: Ratio[Yards[1] & Metres[-1], 1.09361] = erasedValue

trait Miles[Power <: Nat] extends Units[Power, Length]
object Miles:
  erased given milesPerMetre: Ratio[Miles[1] & Metres[-1], 0.000521371] = erasedValue

trait Lightyears[Power <: Nat] extends Units[Power, Length]
object Lightyears:
  erased given lightYearsPerMetre: Ratio[Lightyears[1] & Metres[-1], 1.057E-16] = erasedValue

trait NauticalMiles[Power <: Nat] extends Units[Power, Length]
object NauticalMiles:
  erased given nauticalMilesPerMetre: Ratio[NauticalMiles[1] & Metres[-1], 5.399568034557236E-4] =
    erasedValue

trait Furlongs[Power <: Nat] extends Units[Power, Length]
object Furlongs:
  erased given metresPerFurlong: Ratio[Metres[1] & Furlongs[-1], 201.168] = erasedValue

trait Chains[Power <: Nat] extends Units[Power, Length]
object Chains:
  erased given metresPerChain: Ratio[Metres[1] & Chains[-1], 20.1168] = erasedValue

val Inch = Quantity[Inches[1]](1.0)
val Foot = Quantity[Feet[1]](1.0)
val Yard = Quantity[Yards[1]](1.0)
val Mile = Quantity[Miles[1]](1.0)
val Lightyear = Quantity[Lightyears[1]](1.0)
val NauticalMile = Quantity[NauticalMiles[1]](1.0)
val Furlong = Quantity[Furlongs[1]](1.0)
val Chain = Quantity[Chains[1]](1.0)

// Units of Area

val Are = MetricUnit[Metres[2]](100.0)
val Acre = Furlong*Furlong/10.0

// Units of Volume

val Litre = MetricUnit[Metres[3]](0.001)
val FluidOunce = Milli(Litre)*28.4130625
val Pint = Milli(Litre)*568.26125
val Quart = Milli(Litre)*1136.5225
val Gallon = Milli(Litre)*4546.09

// Units of Mass

trait Grains[Power <: Nat] extends Units[Power, Mass]
object Grains:
  erased given grainsPerGram: Ratio[Grains[1] & Kilograms[-1], 0.0154324] = erasedValue

trait Ounces[Power <: Nat] extends Units[Power, Mass]
object Ounces:
  erased given ouncesPerGram: Ratio[Ounces[1] & Kilograms[-1], 0.000035274] = erasedValue

trait Pounds[Power <: Nat] extends Units[Power, Mass]
object Pounds:
  erased given gramsPerPound: Ratio[Kilograms[1] & Pounds[-1], 0.45359237] = erasedValue

trait Stones[Power <: Nat] extends Units[Power, Mass]
object Stones:
  erased given gramsPerStone: Ratio[Kilograms[1] & Stones[-1], 6.35029318] = erasedValue

trait Hundredweights[Power <: Nat] extends Units[Power, Mass]
object Hundredweights:
  erased given gramsPerHundredweight: Ratio[Kilograms[1] & Hundredweights[-1], 50.80234544] =
    erasedValue

trait Tons[Power <: Nat] extends Units[Power, Mass]
object Tons:
  erased given gramsPerTon: Ratio[Kilograms[1] & Tons[-1], 1016.0469088] = erasedValue

val Grain = Quantity[Grains[1]](1.0)
val Ounce = Quantity[Ounces[1]](1.0)
val Pound = Quantity[Pounds[1]](1.0)
val Stone = Quantity[Stones[1]](1.0)
val Hundredweight = Quantity[Hundredweights[1]](1.0)
val Ton = Quantity[Tons[1]](1.0)

// Units of Time

val Day = Quantity[Days[1]](1.0)
val Hour = Quantity[Hours[1]](1.0)
val Minute = Quantity[Minutes[1]](1.0)

trait SiderealDays[Power <: Nat] extends Units[Power, Time]
object SiderealDays:
  erased given secondsPerSiderealDay: Ratio[Seconds[1] & SiderealDays[-1], 86164.0905] =
    erasedValue

trait Days[Power <: Nat] extends Units[Power, Time]
object Days:
  erased given secondsPerDay: Ratio[Seconds[1] & Days[-1], 86400.0] = erasedValue

trait Hours[Power <: Nat] extends Units[Power, Time]
object Hours:
  erased given secondsPerHour: Ratio[Seconds[1] & Hours[-1], 3600.0] = erasedValue

trait Minutes[Power <: Nat] extends Units[Power, Time]
object Minutes:
  erased given secondsPerMinute: Ratio[Seconds[1] & Minutes[-1], 60.0] = erasedValue
