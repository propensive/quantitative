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
import gossamer.*

import scala.quoted.*

object QuantifyMacros:

  private case class UnitPower(ref: UnitRef, power: Int)
  
  private object UnitsMap:
    def apply[UnitsTypeType <: Measure: Type](using Quotes): UnitsMap =
      import quotes.reflect.*
      
      def recur(repr: TypeRepr): Map[DimensionRef, UnitPower] =
        repr match
          case AndType(left, right) =>
            recur(left) ++ recur(right)
          
          case AppliedType(unit@TypeRef(_, _), List(ConstantType(IntConstant(power)))) =>
            val unitTypeRef = UnitRef(unit.asType, unit.show)
            Map(unitTypeRef.dimensionRef -> UnitPower(unitTypeRef, power))
          
          case other =>
            Map()
      
      new UnitsMap(recur(quotes.reflect.TypeRepr.of[UnitsTypeType]))

  private case class Dimensionality(map: Map[DimensionRef, Int]):
    def quantityName(using Quotes): Option[String] =
      import quotes.reflect.*
      def recur(todo: List[(DimensionRef, Int)], current: TypeRepr): TypeRepr = todo match
        case Nil              => current
        case (dim, n) :: tail => (current.asType, dim.power(n).asType) match
          case ('[current], '[next]) => recur(tail, TypeRepr.of[current & next])
      
      recur(map.to(List), TypeRepr.of[Units[?, ?]]).asType match
        case '[units] => Expr.summon[DimensionName[Units[?, ?] & units, ?]].map:
          case '{ $name: dimType } => Type.of[dimType] match
            case '[DimensionName[_, name]] => TypeRepr.of[name] match
              case ConstantType(StringConstant(name)) => name

  private case class UnitsMap(map: Map[DimensionRef, UnitPower]):
    def repr(using Quotes): Option[quotes.reflect.TypeRepr] = construct(map.values.to(List))
    
    def inverseMap: Map[DimensionRef, UnitPower] =
      map.mapValues { case UnitPower(unit, power) => UnitPower(unit, -power) }.to(Map)

    def dimensionality: Dimensionality = Dimensionality(map.mapValues(_.power).to(Map))
    def dimensions: List[DimensionRef] = map.keys.to(List)
    def empty: Boolean = map.values.forall(_.power == 0)

    def *(that: UnitsMap): UnitsMap = new UnitsMap(
      (dimensions ++ that.dimensions).to(Set).to(List).map: dim =>
        val dimUnit = unit(dim).orElse(that.unit(dim)).get
        dim -> UnitPower(dimUnit, (unitPower(dim) + that.unitPower(dim)))
      .to(Map).filter(_(1).power != 0)
    )
    
    def /(that: UnitsMap): UnitsMap = new UnitsMap(
      (dimensions ++ that.dimensions).to(Set).to(List).map: dim =>
        val dimUnit = unit(dim).orElse(that.unit(dim)).get
        dim -> UnitPower(dimUnit, (unitPower(dim) - that.unitPower(dim)))
      .to(Map).filter(_(1).power != 0)
    )

    def construct(using Quotes)(types: List[UnitPower]): Option[quotes.reflect.TypeRepr] =
      import quotes.reflect.*

      types.filter(_.power != 0) match
        case Nil =>
          None
        
        case UnitPower(unit, power) :: Nil =>
          Some(AppliedType(unit.ref, List(ConstantType(IntConstant(power)))))
        
        case UnitPower(unit, power) :: more =>
          Some(AndType(AppliedType(unit.ref, List(ConstantType(IntConstant(power)))),
              construct(more).get))
    
    def sub(dimension: DimensionRef, unit: UnitRef, power: Int): UnitsMap =
      new UnitsMap(map.updated(dimension, UnitPower(unit, power)))
    
    def unit(dimension: DimensionRef): Option[UnitRef] = map.get(dimension).map(_.ref)
    def unitPower(dimension: DimensionRef): Int = map.get(dimension).map(_.power).getOrElse(0)
  

  private class UnitRef(val typ: Type[?], val name: String):
    def ref(using Quotes): quotes.reflect.TypeRepr =
      typ match { case '[ref] => quotes.reflect.TypeRepr.of[ref] }
    
    def dimensionRef(using Quotes): DimensionRef =
      import quotes.reflect.*
      
      typ match
        case '[ Units[power, unitType] ] => TypeRepr.of[unitType] match
          case ref@TypeRef(_, _) => DimensionRef(ref.asType, ref.show)

    def power(n: Int)(using Quotes): quotes.reflect.TypeRepr =
      import quotes.reflect.*
      AppliedType(ref, List(ConstantType(IntConstant(n))))
    
    override def equals(that: Any): Boolean = that match
      case that: UnitRef => name == that.name
      case _                 => false
    
    override def hashCode: Int = name.hashCode
    override def toString(): String = name

  private class DimensionRef(val typ: Type[?], val name: String):
    def ref(using Quotes): quotes.reflect.TypeRepr =
      typ match { case '[ref] => quotes.reflect.TypeRepr.of[ref] }
    
    def dimensionality(using Quotes): Dimensionality = Dimensionality(Map(this -> 1))
    
    def power(n: Int)(using Quotes): quotes.reflect.TypeRepr =
      import quotes.reflect.*
      
      (ConstantType(IntConstant(n)).asType, ref.asType) match
        case ('[power], '[dimension]) => TypeRepr.of[Units[power & Nat, dimension & Dimension]]
    
    def principal(using Quotes): UnitRef =
      import quotes.reflect.*
      
      typ match
        case '[dim] => Expr.summon[PrincipalUnit[dim & Dimension, ?]] match
          case None =>
            val dimensionName =
              dimensionality.quantityName.map: name =>
                "the physical quantity "+name
              .getOrElse("the same quantity")

            fail(txt"""
              the operands both represent ${dimensionName}, but there is no principal unit specified
              for this dimension
            """.s)
          case Some('{ $expr: principalUnit }) => Type.of[principalUnit] match
            case '[ PrincipalUnit[dim, units] ] => TypeRepr.of[units] match
              case TypeLambda(_, _, AppliedType(ref@TypeRef(_, _), _)) =>
                UnitRef(ref.asType, ref.show)
              case other =>
                fail(s"principal units had an unexpected type: $other")
        
    
    override def equals(that: Any): Boolean = that match
      case that: DimensionRef => name == that.name
      case _                      => false
    
    override def hashCode: Int = name.hashCode
    override def toString(): String = name

  private def ratio
      (using Quotes)
      (from: UnitRef, to: UnitRef, power: Int, retry: Boolean = true)
      : Expr[Double] =
    import quotes.reflect.*

    if from == to then Expr(1.0) else (from.power(-1).asType, to.power(1).asType) match
      case ('[from], '[to]) =>
        Expr.summon[Ratio[from & to & Measure]] match
          case None =>
            if retry then ratio(to, from, -power, false)
            else
              val quantityName = from.dimensionRef.dimensionality.quantityName
              
              val dimensionName = quantityName.map("the physical quantity "+_).getOrElse:
                  "the same physical quantity"
              
              fail(txt"""
                both operands represent $dimensionName, but the coversion ratio between them is not
                known
  
                To provide the conversion ratio, please provide a contextual instance in scope, with
                the type, `Ratio[${from.name}[1] & ${to.name}[-1]]`, or `Ratio[${to.name}[1] &
                ${from.name}[-1]]`.
              """.s)
          case Some(ratio) =>
            if power == 1 then '{$ratio.value.value}
            else '{math.pow($ratio.value.value, ${Expr(power)})}

  private def normalize
      (using Quotes)
      (units: UnitsMap, other: UnitsMap, init: Expr[Double], force: Boolean = false)
      : (UnitsMap, Expr[Double]) =
    def recur
        (dimensions: List[DimensionRef], target: UnitsMap, expr: Expr[Double])
        : (UnitsMap, Expr[Double]) =
      dimensions match
        case Nil =>
          (target, expr)
        
        case dimension :: dimensions =>
          if other.unitPower(dimension) == 0 || units.unit(dimension) == other.unit(dimension)
          then recur(dimensions, target, expr)
          else
            val unit = target.unit(dimension).get
            val power = target.unitPower(dimension)
            
            val unit2 =
              if force then other.unit(dimension).orElse(target.unit(dimension)).get
              else dimension.principal
            
            val value = '{$expr*${ratio(unit, unit2, power)}}
            recur(dimensions, target.sub(dimension, unit2, power), value)
    
    recur(units.dimensions, units, init)
  
  def collectUnits[UnitsType <: Measure: Type](using Quotes): Expr[Map[Text, Int]] =
    import quotes.reflect.*
    
    def recur(expr: Expr[Map[Text, Int]], todo: List[UnitPower]): Expr[Map[Text, Int]] =
      todo match
        case Nil =>
          expr
        
        case UnitPower(unit, power) :: todo2 =>
          AppliedType(unit.ref, List(ConstantType(IntConstant(1)))).asType match
            case '[ refType ] =>
              val unitName = Expr.summon[UnitName[refType]].get
              recur('{$expr.updated($unitName.text, ${Expr(power)})}, todo2)
          
            case _ =>
              throw Mistake("Should never match")
    
    recur('{Map[Text, Int]()}, UnitsMap[UnitsType].map.values.to(List))

  def multiply
      [LeftType <: Measure: Type, RightType <: Measure: Type]
      (leftExpr: Expr[Quantity[LeftType]], rightExpr: Expr[Quantity[RightType]], division: Boolean)
      (using Quotes)
      : Expr[Any] =
    import quotes.reflect.*

    val left: UnitsMap = UnitsMap[LeftType]
    val right: UnitsMap = UnitsMap[RightType]
    
    val (left2, leftValue) = normalize(left, right, '{$leftExpr.value})
    val (right2, rightValue) = normalize(right, left, '{$rightExpr.value})

    val resultUnits = if division then left2/right2 else left2*right2
    val resultValue = if division then '{$leftValue/$rightValue} else '{$leftValue*$rightValue}
    
    resultUnits.repr.map(_.asType) match
      case Some('[units]) => '{Quantity[units & Measure]($resultValue)}
      case None           => resultValue

  private def incompatibleTypes(left: UnitsMap, right: UnitsMap)(using Quotes): Nothing =
    (left.dimensionality.quantityName, right.dimensionality.quantityName) match
      case (Some(leftName), Some(rightName)) =>
        fail(txt"""
          the left operand represents $leftName, but the right operand represents $rightName; these
          are incompatible physical quantities
        """.s)
      
      case _ =>
        fail("the operands represent different physical quantities")

  def greaterThan
      [LeftType <: Measure: Type, RightType <: Measure: Type]
      (leftExpr: Expr[Quantity[LeftType]], rightExpr: Expr[Quantity[RightType]], closed: Boolean)
      (using Quotes)
      : Expr[Boolean] =
    import quotes.reflect.*

    val left: UnitsMap = UnitsMap[LeftType]
    val right: UnitsMap = UnitsMap[RightType]

    val (left2, leftValue) = normalize(left, right, '{$leftExpr.value})
    val (right2, rightValue) = normalize(right, left, '{$rightExpr.value})

    if left2 != right2 then incompatibleTypes(left, right)

    if closed then '{$leftValue >= $rightValue} else '{$leftValue > $rightValue}

  def add
      [LeftType <: Measure: Type, RightType <: Measure: Type]
      (leftExpr: Expr[Quantity[LeftType]], rightExpr: Expr[Quantity[RightType]], sub: Boolean)
      (using Quotes)
      : Expr[Any] =
    import quotes.reflect.*

    val left: UnitsMap = UnitsMap[LeftType]
    val right: UnitsMap = UnitsMap[RightType]

    val (left2, leftValue) = normalize(left, right, '{$leftExpr.value})
    val (right2, rightValue) = normalize(right, left, '{$rightExpr.value})

    if left2 != right2 then incompatibleTypes(left, right)
    
    val resultValue = if sub then '{$leftValue - $rightValue} else '{$leftValue + $rightValue}
    
    left2.repr.map(_.asType) match
      case Some('[units]) => '{Quantity[units & Measure]($resultValue)}
      case None           => resultValue

  def norm
      [UnitsType <: Measure: Type, NormType[power <: Nat] <: Units[power, ?]: Type]
      (expr: Expr[Quantity[UnitsType]])(using Quotes)
      : Expr[Any] =
    import quotes.reflect.*
    val units: UnitsMap = UnitsMap[UnitsType]
    val norm: UnitsMap = UnitsMap[NormType[1]]
    val (units2, value) = normalize(units, norm, '{$expr.value}, true)

    units2.repr.map(_.asType) match
      case Some('[units]) => '{Quantity[units & Measure]($value)}
      case None           => value
  
  def describe[UnitsType <: Measure: Type](using Quotes): Expr[Text] =
    import quotes.reflect.*
    
    UnitsMap[UnitsType].dimensionality.quantityName match
      case Some(name) => '{Text(${Expr(name)})}
      case None       => fail("there is no descriptive name for this physical quantity")
    