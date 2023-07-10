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

import anticipation.*

import language.experimental.captureChecking

package constants:
  val SpeedOfLightInVacuum = 299792458*Metre/Second
  val MagneticConstant = 4*math.Pi*(10e-7)*Newton/(Ampere*Ampere)
  val ElectricConstant = 8.854187817e-12*Farad/Metre
  val CharacteristicImpedanceOfVacuum = 376.730313461*Ohm
  val NewtonianConstantOfGravitation = 6.673e-11*Metre*Metre*Metre/Kilo(Gram)/Second/Second
  val PlanckConstant = 6.62607015e-34*Metre*Metre*Kilo(Gram)/Second

  // FIXME: Add more
