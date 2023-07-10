/*
    Quantitative, version [unreleased]. Copyright 2023 Jon Pretty, Propensive OÜ.

    The primary distribution site is: https://propensive.com/

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
    file except in compliance with the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the
    License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    either express or implied. See the License for the specific language governing permissions
    and limitations under the License.
*/

package quantitative

import gossamer.*
import rudiments.*
import anticipation.*
import spectacular.*

import scala.quoted.*

import language.implicitConversions
import language.experimental.captureChecking

trait Dimension

erased trait Length extends Dimension
erased trait Mass extends Dimension
erased trait Time extends Dimension
erased trait Current extends Dimension
erased trait Luminosity extends Dimension
erased trait Temperature extends Dimension
erased trait AmountOfSubstance extends Dimension

erased trait Angle extends Dimension

erased trait PhysicalQuantity[DimensionType <: Units[?, ?], LabelType <: Label]()

object PhysicalQuantity:
  // base units
  erased given length: PhysicalQuantity[Units[1, Length], "length"] = ###
  erased given mass: PhysicalQuantity[Units[1, Mass], "mass"] = ###
  erased given time: PhysicalQuantity[Units[1, Time], "time"] = ###
  erased given current: PhysicalQuantity[Units[1, Current], "current"] = ###
  erased given temperature: PhysicalQuantity[Units[1, Temperature], "temperature"] = ###
  erased given luminosity: PhysicalQuantity[Units[1, Luminosity], "luminosity"] = ###
  
  erased given angle: PhysicalQuantity[Units[1, Angle], "angle"] = ###
  
  // derived units from https://en.wikipedia.org/wiki/List_of_physical_quantities
  
  type ElectricalConductivity = Units[-3, Length] & Units[-1, Mass] & Units[3, Time] & Units[2, Current]
  type Permittivity = Units[-3, Length] & Units[-1, Mass] & Units[4, Time] & Units[2, Current]
  type ReactionRate = Units[-3, Length] & Units[-1, Time] & Units[1, AmountOfSubstance]
  type MolarConcentration = Units[-3, Length] & Units[1, AmountOfSubstance]
  type ElectricChargeDensity = Units[-3, Length] & Units[1, Time] & Units[1, Current]
  type MassDensity = Units[-3, Length] & Units[1, Mass]
  type Reluctance = Units[-2, Length] & Units[-1, Mass] & Units[2, Time] & Units[2, Current]
  type ElectricalConductance = Units[-2, Length] & Units[-1, Mass] & Units[3, Time] & Units[2, Current]
  type ThermalResistance = Units[-2, Length] & Units[-1, Mass] & Units[3, Time] & Units[1, Temperature]
  type Capacitance = Units[-2, Length] & Units[-1, Mass] & Units[4, Time] & Units[1, Current]
  type CurrentDensity = Units[-2, Length] & Units[1, Current]
  type ElectricDisplacementField = Units[-2, Length] & Units[1, Time] & Units[1, Current]
  type Illuminance = Units[-2, Length] & Units[1, Luminosity]
  type AreaDensity = Units[-2, Length] & Units[1, Mass]
  type ThermalResistivity = Units[-1, Length] & Units[-1, Mass] & Units[3, Time] & Units[1, Temperature]
  type Magnetization = Units[-1, Length] & Units[1, Current]
  type OpticalPower = Units[-1, Length]
  type TempratureGradient = Units[-1, Length] & Units[1, Temperature]
  type Pressure = Units[-1, Length] & Units[1, Mass] & Units[-2, Time]
  type DynamicViscosity = Units[-1, Length] & Units[1, Mass] & Units[-1, Time]
  type LinearDensity = Units[-1, Length] & Units[1, Mass]
  type Frequency = Units[-1, Time]
  type ElectricCharge = Units[1, Time] & Units[1, Current]
  type Radiance = Units[1, Mass] & Units[-3, Time]
  type MagneticFluxDensity = Units[1, Mass] & Units[-2, Time] & Units[-1, Current]
  type SurfaceTension = Units[1, Mass] & Units[-2, Time]
  type Absement = Units[1, Mass] & Units[1, Time]
  type Pop = Units[1, Length] & Units[-6, Time]
  type Crackle = Units[1, Length] & Units[-5, Time]
  type Jounce = Units[1, Length] & Units[-4, Time]
  type Jerk = Units[1, Length] & Units[-3, Time]
  type Acceleration = Units[1, Length] & Units[-2, Time]
  type Velocity = Units[1, Length] & Units[-1, Time]
  type ElectricDipoleMoment = Units[1, Length] & Units[1, Time] & Units[1, Current]
  type ElectricFieldStrength = Units[1, Length] & Units[1, Mass] & Units[-3, Time] & Units[-1, Current]
  type ThermalConductivity = Units[1, Length] & Units[1, Mass] & Units[-3, Time] & Units[-1, Temperature]
  type Permeability = Units[1, Length] & Units[1, Mass] & Units[-2, Time] & Units[-2, Current]
  type Force = Units[1, Length] & Units[1, Mass] & Units[-2, Time]
  type Momentum = Units[1, Length] & Units[1, Mass] & Units[-1, Time]
  type AbsorbedDoseRate = Units[2, Length] & Units[-3, Time]
  type SpecificHeatCapacity = Units[2, Length] & Units[-2, Time] & Units[-1, Temperature]
  type SpecificEnergy = Units[2, Length] & Units[-2, Time]
  type Area = Units[2, Length]
  type MagneticMoment = Units[2, Length] & Units[1, Current]
  type Impedance = Units[2, Length] & Units[1, Mass] & Units[-3, Time] & Units[-2, Current]
  type ElectricalPotential = Units[2, Length] & Units[1, Mass] & Units[-3, Time] & Units[-1, Current]
  type ThermalConductance = Units[2, Length] & Units[1, Mass] & Units[-3, Time] & Units[-1, Temperature]
  type Power = Units[2, Length] & Units[1, Mass] & Units[-3, Time]
  type Inductance = Units[2, Length] & Units[1, Mass] & Units[-2, Time] & Units[-2, Current]
  type MagneticFlux = Units[2, Length] & Units[1, Mass] & Units[-2, Time] & Units[-1, Current]
  type Entropy = Units[2, Length] & Units[1, Mass] & Units[-2, Time] & Units[-1, Temperature]
  type MolarEntropy = Units[2, Length] & Units[1, Mass] & Units[-2, Time] & Units[-1, Temperature] & Units[-1, AmountOfSubstance]
  type ChemicalPotential = Units[2, Length] & Units[1, Mass] & Units[-2, Time] & Units[-1, AmountOfSubstance]
  type Energy = Units[2, Length] & Units[1, Mass] & Units[-2, Time]
  type Spin = Units[2, Length] & Units[1, Mass] & Units[-1, Time]
  type MomentOfInertia = Units[2, Length] & Units[1, Mass]
  type SpecificVolume = Units[3, Length] & Units[-1, Mass]
  type VolumetricFlowRate = Units[3, Length] & Units[-1, Time]
  type Volume = Units[3, Length]
  type ElectricalResistivity = Units[3, Length] & Units[1, Mass] & Units[-3, Time] & Units[-2, Current]
  
  // type L[-NatType <: Nat] = Units[Nat, Length]
  // type M[-NatType <: Nat] = Units[Nat, Mass]
  // type T[-NatType <: Nat] = Units[Nat, Time]
  // type I[-NatType <: Nat] = Units[Nat, Current]
  // type N[-NatType <: Nat] = Units[Nat, AmountOfSubstance]
  // type H[-NatType <: Nat] = Units[Nat, Temperature]
  // type J[-NatType <: Nat] = Units[Nat, Luminosity]

  // type ElectricalConductivity    = L[-3] & M[-1] & T[ 3] & I[ 2]
  // type Permittivity              = L[-3] & M[-1] & T[ 4] & I[ 2]
  // type ReactionRate              = L[-3] &         T[-1] &                 N[ 1]
  // type MolarConcentration        = L[-3] &                                 N[ 1]
  // type ElectricChargeDensity     = L[-3] &         T[ 1] & I[ 1]
  // type MassDensity               = L[-3] & M[ 1]
  // type Reluctance                = L[-2] & M[-1] & T[ 2] & I[ 2]
  // type ElectricalConductance     = L[-2] & M[-1] & T[ 3] & I[ 2]
  // type ThermalResistance         = L[-2] & M[-1] & T[ 3] &         H[ 1]
  // type Capacitance               = L[-2] & M[-1] & T[ 4] & I[ 1]
  // type CurrentDensity            = L[-2] &                 I[ 1]
  // type ElectricDisplacementField = L[-2] &         T[ 1] & I[ 1]
  // type Illuminance               = L[-2] &                                         J[ 1]
  // type AreaDensity               = L[-2] & M[ 1]
  // type ThermalResistivity        = L[-1] & M[-1] & T[ 3] &         H[ 1]
  // type Magnetization             = L[-1] &                 I[ 1]
  // type OpticalPower              = L[-1]
  // type TempratureGradient        = L[-1] &                         H[ 1]
  // type Pressure                  = L[-1] & M[ 1] & T[-2]
  // type DynamicViscosity          = L[-1] & M[ 1] & T[-1]
  // type LinearDensity             = L[-1] & M[ 1]
  // type Frequency                 =                 T[-1]
  // type ElectricCharge            =                 T[ 1] & I[ 1]
  // type Radiance                  =         M[ 1] & T[-3]
  // type MagneticFluxDensity       =         M[ 1] & T[-2] & I[-1]
  // type SurfaceTension            =         M[ 1] & T[-2]
  // type Absement                  =         M[ 1] & T[ 1]
  // type Pop                       = L[ 1] &         T[-6]
  // type Crackle                   = L[ 1] &         T[-5]
  // type Jounce                    = L[ 1] &         T[-4]
  // type Jerk                      = L[ 1] &         T[-3]
  // type Acceleration              = L[ 1] &         T[-2]
  // type Velocity2                  = L[ 1] &         T[-1]
  // type Velocity = Units[1, Length] & Units[-1, Time]
  // type ElectricDipoleMoment      = L[ 1] &         T[ 1] & I[ 1]
  // type ElectricFieldStrength     = L[ 1] & M[ 1] & T[-3] & I[-1]
  // type ThermalConductivity       = L[ 1] & M[ 1] & T[-3] &         H[-1]
  // type Permeability              = L[ 1] & M[ 1] & T[-2] & I[-2]
  // type Force                     = L[ 1] & M[ 1] & T[-2]
  // type Momentum                  = L[ 1] & M[ 1] & T[-1]
  // type AbsorbedDoseRate          = L[ 2] &         T[-3]
  // type SpecificHeatCapacity      = L[ 2] &         T[-2] &         H[-1]
  // type SpecificEnergy            = L[ 2] &         T[-2]
  // type Area                      = L[ 2]
  // type MagneticMoment            = L[ 2] &                 I[ 1]
  // type Impedance                 = L[ 2] & M[ 1] & T[-3] & I[-2]
  // type ElectricalPotential       = L[ 2] & M[ 1] & T[-3] & I[-1]
  // type ThermalConductance        = L[ 2] & M[ 1] & T[-3] &         H[-1]
  // type Power                     = L[ 2] & M[ 1] & T[-3]
  // type Inductance                = L[ 2] & M[ 1] & T[-2] & I[-2]
  // type MagneticFlux              = L[ 2] & M[ 1] & T[-2] & I[-1]
  // type Entropy                   = L[ 2] & M[ 1] & T[-2] &         H[-1]
  // type MolarEntropy              = L[ 2] & M[ 1] & T[-2] &         H[-1] & N[-1]
  // type ChemicalPotential         = L[ 2] & M[ 1] & T[-2] &                 N[-1]
  // type Energy                    = L[ 2] & M[ 1] & T[-2]
  // type Spin                      = L[ 2] & M[ 1] & T[-1]
  // type MomentOfInertia           = L[ 2] & M[ 1]
  // type SpecificVolume            = L[ 3] & M[-1]
  // type VolumetricFlowRate        = L[ 3] &         T[-1]
  // type Volume                    = L[ 3]
  // type ElectricalResistivity     = L[ 3] & M[ 1] & T[-3] & I[-2]

  // erased given heat: PhysicalQuantity[Heat, "heat"] = ###
  erased given absement: PhysicalQuantity[Absement, "absement"] = ###
  erased given absorbedDoseRate: PhysicalQuantity[AbsorbedDoseRate, "absorbed dose rate"] = ###
  erased given acceleration: PhysicalQuantity[Acceleration, "acceleration"] = ###
  erased given area: PhysicalQuantity[Area, "area"] = ###
  erased given areaDensity: PhysicalQuantity[AreaDensity, "area density"] = ###
  erased given capacitance: PhysicalQuantity[Capacitance, "capacitance"] = ###
  erased given crackle: PhysicalQuantity[Crackle, "crackle"] = ###
  erased given currentDensity: PhysicalQuantity[CurrentDensity, "current density"] = ###
  erased given dynamicViscosity: PhysicalQuantity[DynamicViscosity, "dynamic viscosity"] = ###
  erased given electricCharge: PhysicalQuantity[ElectricCharge, "electric charge"] = ###
  erased given energy: PhysicalQuantity[Energy, "energy"] = ###
  erased given entropy: PhysicalQuantity[Entropy, "entropy"] = ###
  erased given force: PhysicalQuantity[Force, "force"] = ###
  erased given frequency: PhysicalQuantity[Frequency, "frequency"] = ###
  erased given substance: PhysicalQuantity[Units[1, AmountOfSubstance], "amount of substance"] = ###
  erased given illuminance: PhysicalQuantity[Illuminance, "illuminance"] = ###
  erased given impedance: PhysicalQuantity[Impedance, "impedance"] = ###
  erased given inductance: PhysicalQuantity[Inductance, "inductance"] = ###
  erased given jerk: PhysicalQuantity[Jerk, "jerk"] = ###
  erased given jounce: PhysicalQuantity[Jounce, "jounce"] = ###
  erased given linearDensity: PhysicalQuantity[LinearDensity, "linear density"] = ###
  erased given magneticFlux: PhysicalQuantity[MagneticFlux, "magnetic flux"] = ###
  erased given magneticMoment: PhysicalQuantity[MagneticMoment, "magnetic moment"] = ###
  erased given magnetization: PhysicalQuantity[Magnetization, "magnetization"] = ###
  erased given massDensity: PhysicalQuantity[MassDensity, "mass density"] = ###
  erased given molarConcentration: PhysicalQuantity[MolarConcentration, "molar concentration"] = ###
  erased given chemicalPotential: PhysicalQuantity[ChemicalPotential, "chemical potential"] = ###
  erased given molarEntropy: PhysicalQuantity[MolarEntropy, "molar entropy"] = ###
  erased given momentOfInertia: PhysicalQuantity[MomentOfInertia, "moment of inertia"] = ###
  erased given momentum: PhysicalQuantity[Momentum, "momentum"] = ###
  erased given opticalPower: PhysicalQuantity[OpticalPower, "optical power"] = ###
  erased given permeability: PhysicalQuantity[Permeability, "permeability"] = ###
  erased given permittivity: PhysicalQuantity[Permittivity, "permittivity"] = ###
  erased given power: PhysicalQuantity[Power, "power"] = ###
  erased given pressure: PhysicalQuantity[Pressure, "pressure"] = ###
  erased given pop: PhysicalQuantity[Pop, "pop"] = ###
  erased given radiance: PhysicalQuantity[Radiance, "radiance"] = ###
  erased given reactionRate: PhysicalQuantity[ReactionRate, "reaction rate"] = ###
  erased given reluctance: PhysicalQuantity[Reluctance, "reluctance"] = ###
  erased given specificEnergy: PhysicalQuantity[SpecificEnergy, "specific energy"] = ###
  erased given specificVolume: PhysicalQuantity[SpecificVolume, "specific volume"] = ###
  erased given spin: PhysicalQuantity[Spin, "spin"] = ###
  erased given surfaceTension: PhysicalQuantity[SurfaceTension, "surface tension"] = ###
  erased given thermalConductance: PhysicalQuantity[ThermalConductance, "thermal conductance"] = ###
  erased given thermalResistance: PhysicalQuantity[ThermalResistance, "thermal resistance"] = ###
  erased given thermalResistivity: PhysicalQuantity[ThermalResistivity, "thermal resistivity"] = ###
  erased given velocity: PhysicalQuantity[Velocity, "velocity"] = ###
  erased given volume: PhysicalQuantity[Volume, "volume"] = ###

  erased given electricChargeDensity: PhysicalQuantity[ElectricChargeDensity,
      "electric charge density"] = ###
  
  erased given electricDipoleMoment
      : PhysicalQuantity[ElectricDipoleMoment, "electric dipole moment"] =
    ###
  
  erased given electricDisplacementField: PhysicalQuantity[ElectricDisplacementField,
      "electric displacement field"] = ###
  
  erased given electricFieldStrength: PhysicalQuantity[ElectricFieldStrength,
      "electric field strength"] = ###
  
  erased given electricalConductance
      : PhysicalQuantity[ElectricalConductance, "electric conductance"] =
    ###
  
  erased given electricalConductivity: PhysicalQuantity[ElectricalConductivity,
      "electric conductivity"] = ###
  
  erased given electricalPotential: PhysicalQuantity[ElectricalPotential, "electric potential"] =
    ###
  
  erased given electricalResistivity
      : PhysicalQuantity[ElectricalResistivity, "electric resistivity"] =
    ###
  
  erased given magneticFluxDensity: PhysicalQuantity[MagneticFluxDensity, "magnetic flux density"] =
    ###
  
  erased given specificHeatCapacity
      : PhysicalQuantity[SpecificHeatCapacity, "specific heat capacity"] =
    ###
  
  erased given thermalConductivity: PhysicalQuantity[ThermalConductivity, "thermal conductivity"] =
    ###
  
  erased given volumetricFlowRate: PhysicalQuantity[VolumetricFlowRate, "volumetric flow rate"] =
    ###
  
sealed trait Measure

trait Units[PowerType <: Nat, DimensionType <: Dimension] extends Measure

erased trait Metres[Power <: Nat] extends Units[Power, Length]
erased trait Kilograms[Power <: Nat] extends Units[Power, Mass]
erased trait Candelas[Power <: Nat] extends Units[Power, Luminosity]
erased trait Moles[Power <: Nat] extends Units[Power, AmountOfSubstance]
erased trait Amperes[Power <: Nat] extends Units[Power, Current]
erased trait Kelvins[Power <: Nat] extends Units[Power, Temperature]
erased trait Seconds[Power <: Nat] extends Units[Power, Time]

erased trait Radians[Power <: Nat] extends Units[Power, Angle]

trait UnitName[-ValueType]:
  def siPrefix: MetricPrefix = NoPrefix
  def name(): Text
  def text: Text = t"${siPrefix.symbol}${name()}"

object UnitName:
  given UnitName[Metres[1]] = () => t"m"
  given UnitName[Candelas[1]] = () => t"cd"
  given UnitName[Moles[1]] = () => t"mol"
  given UnitName[Amperes[1]] = () => t"A"
  given UnitName[Kelvins[1]] = () => t"K"
  given UnitName[Seconds[1]] = () => t"s"
  
  given UnitName[Radians[1]] = () => t"rad"

  given UnitName[Kilograms[1]] with
    override def siPrefix: MetricPrefix = Kilo
    def name(): Text = t"g"

trait PrincipalUnit[DimensionType <: Dimension, UnitType[_ <: Nat] <: Measure]()

object PrincipalUnit:
  given length: PrincipalUnit[Length, Metres]()
  given mass: PrincipalUnit[Mass, Kilograms]()
  given time: PrincipalUnit[Time, Seconds]()
  given current: PrincipalUnit[Current, Amperes]()
  given luminosity: PrincipalUnit[Luminosity, Candelas]()
  given temperature: PrincipalUnit[Temperature, Kelvins]()
  given amountOfSubstance: PrincipalUnit[AmountOfSubstance, Moles]()

  given angle: PrincipalUnit[Angle, Radians]()

trait SubstituteUnits[UnitsType <: Measure](val name: Text)

object SubstituteUnits:
  given joules: SubstituteUnits[Kilograms[1] & Metres[2] & Seconds[-2]](t"J")
  given newtons: SubstituteUnits[Kilograms[1] & Metres[1] & Seconds[-2]](t"N")

trait UnitsOffset[UnitsType <: Measure]:
  def value(): Double

object Quantitative:
  opaque type Quantity[UnitsType <: Measure] = Double
  opaque type MetricUnit[UnitsType <: Measure] <: Quantity[UnitsType] = Double

  extension [UnitsType <: Measure](quantity: Quantity[UnitsType])
    def underlying: Double = quantity
    inline def value: Double = compiletime.summonFrom:
      case unitsOffset: UnitsOffset[UnitsType] => quantity - unitsOffset.value()
      case _                                   => quantity
  
  extension [UnitsType <: Measure](inline quantity: Quantity[UnitsType])
    inline def tally[TallyType <: Tuple]: Tally[TallyType] =
      ${QuantitativeMacros.fromQuantity[UnitsType, TallyType]('quantity)}

  object MetricUnit:
    def apply[UnitsType <: Measure](value: Double): MetricUnit[UnitsType] = value

    @targetName("makeDerivedUnit")
    def apply[UnitsType <: Measure](value: Quantity[UnitsType]): MetricUnit[UnitsType] = value

  object Quantity:
    erased given [UnitsType <: Measure]: CanEqual[Quantity[UnitsType], Quantity[UnitsType]] = ###

    transparent inline given add[LeftType <: Measure, RightType <: Measure]
        : Add[Quantity[LeftType], Quantity[RightType]] =
      ${QuantitativeMacros.addTypeclass[LeftType, RightType]}

    transparent inline given multiply[LeftType <: Measure, RightType <: Measure]
        : Multiply[Quantity[LeftType], Quantity[RightType]] =
      ${QuantitativeMacros.multiplyTypeclass[LeftType, RightType]}
    
    inline given multiplyDouble[RightType <: Measure]: Multiply[Double, Quantity[RightType]] with
      type Result = Quantity[RightType]
      def apply(left: Double, right: Quantity[RightType]): Quantity[RightType] =
        Quantity(right.value*left)

    inline given multiplyInt[RightType <: Measure]: Multiply[Int, Quantity[RightType]] with
      type Result = Quantity[RightType]
      def apply(left: Int, right: Quantity[RightType]): Quantity[RightType] =
        Quantity(right.value*left.toDouble)

    inline given multiplyDouble2[LeftType <: Measure]: Multiply[Quantity[LeftType], Double] with
      type Result = Quantity[LeftType]
      def apply(left: Quantity[LeftType], right: Double): Quantity[LeftType] =
        Quantity(right*left.value)

    inline given multiplyInt2[LeftType <: Measure]: Multiply[Quantity[LeftType], Int] with
      type Result = Quantity[LeftType]
      def apply(left: Quantity[LeftType], right: Int): Quantity[LeftType] =
        Quantity(right.toDouble*left)

    inline def apply[UnitsType <: Measure](value: Double): Quantity[UnitsType] = value
    
    given convertDouble[UnitsType <: Measure]: Conversion[Double, Quantity[UnitsType]] =
      Quantity(_)
    
    given convertInt[UnitsType <: Measure]: Conversion[Int, Quantity[UnitsType]] = int =>
      Quantity(int.toDouble)

    given inequality
        [UnitsType <: Measure, UnitsType2 <: Measure]
        : Inequality[Quantity[UnitsType], Quantity[UnitsType2]] with
      inline def compare
          (inline left: Quantity[UnitsType], inline right: Quantity[UnitsType2],
              inline strict: Boolean, inline greaterThan: Boolean)
          : Boolean =
        ${QuantitativeMacros.greaterThan[UnitsType, UnitsType2]('left, 'right, 'strict,
            'greaterThan)}
      

    inline given [UnitsType <: Measure](using Decimalizer): Show[Quantity[UnitsType]] =
      new Show[Quantity[UnitsType]]:
        def apply(value: Quantity[UnitsType]): Text = value.render
    
    inline given [UnitsType <: Measure](using Decimalizer): Debug[Quantity[UnitsType]] =
      new Debug[Quantity[UnitsType]]:
        def apply(value: Quantity[UnitsType]): Text = value.render
  
    def renderUnits(units: Map[Text, Int]): Text =
      units.to(List).map: (unit, power) =>
        if power == 1 then unit
        else 
          val exponent: Text =
            power.show.mapChars:
              case '0' => '⁰'
              case '1' => '¹'
              case '2' => '²'
              case '3' => '³'
              case '4' => '⁴'
              case '5' => '⁵'
              case '6' => '⁶'
              case '7' => '⁷'
              case '8' => '⁸'
              case '9' => '⁹'
              case '-' => '¯'
              case _   => ' '
          
          t"$unit$exponent"
      .join(t"·")

export Quantitative.{Quantity, MetricUnit}

val Metre: MetricUnit[Metres[1]] = MetricUnit(1)
val Gram: MetricUnit[Kilograms[1]] = MetricUnit(0.001)
val Candela: MetricUnit[Candelas[1]] = MetricUnit(1)
val Mole: MetricUnit[Moles[1]] = MetricUnit(1)
val Ampere: MetricUnit[Amperes[1]] = MetricUnit(1)
val Kelvin: MetricUnit[Kelvins[1]] = MetricUnit(1)
val Second: MetricUnit[Seconds[1]] = MetricUnit(1)

val Radian: MetricUnit[Radians[1]] = MetricUnit(1)

extension [UnitsType <: Measure](inline quantity: Quantity[UnitsType])
  // @targetName("plus")
  // transparent inline def +[UnitsType2 <: Measure](quantity2: Quantity[UnitsType2]): Any =
  //   ${QuantitativeMacros.add[UnitsType, UnitsType2]('quantity, 'quantity2, '{false})}
  
  // @targetName("minus")
  // transparent inline def -[UnitsType2 <: Measure](quantity2: Quantity[UnitsType2]): Any =
  //   ${QuantitativeMacros.add[UnitsType, UnitsType2]('quantity, 'quantity2, '{true})}

  transparent inline def invert: Any = Quantity[Measure](1.0)/quantity

  transparent inline def in[UnitsType2[power <: Nat] <: Units[power, ?]]: Any =
    ${QuantitativeMacros.norm[UnitsType, UnitsType2]('quantity)}
  
  // @targetName("times2")
  // transparent inline def *
  //     [UnitsType2 <: Measure](@convertible inline quantity2: Quantity[UnitsType2]): Any =
  //   ${QuantitativeMacros.multiply[UnitsType, UnitsType2]('quantity, 'quantity2, false)}
  
  @targetName("divide2")
  transparent inline def /
      [UnitsType2 <: Measure](@convertible inline quantity2: Quantity[UnitsType2]): Any =
    ${QuantitativeMacros.multiply[UnitsType, UnitsType2]('quantity, 'quantity2, true)}

  inline def units: Map[Text, Int] = ${QuantitativeMacros.collectUnits[UnitsType]}
  inline def render(using Decimalizer): Text = t"${quantity.value} ${Quantity.renderUnits(units)}"

  inline def dimension: Text = ${QuantitativeMacros.describe[UnitsType]}

extension (value: Double)
  // @targetName("times")
  // def *[UnitsType <: Measure](quantity: Quantity[UnitsType]): Quantity[UnitsType] = quantity*value
  
  @targetName("divide")
  transparent inline def /[UnitsType <: Measure](quantity: Quantity[UnitsType]): Any =
    ((1.0/value)*quantity).invert
  
