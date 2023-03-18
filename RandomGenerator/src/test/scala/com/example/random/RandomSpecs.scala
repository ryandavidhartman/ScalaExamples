package com.example.random

import org.scalatest.WordSpec

class RandomSpecs extends WordSpec{
  "The integerGenerator" when {
    "run" should {
      "make random integers" in {
        val test1 = integerGenerator.generate
        assert(test1.isInstanceOf[Int])
      }
      "not return the same in every time" in {
        val testVal = integerGenerator.generate
        test[Int](integerGenerator, 100)(x => x != testVal)
      }
    }
  }

  "The integerRangeGenerator" when {
    "run" should {
      "make random integers" in {
        val test1 = integerRangeGenerator(0, 100).generate
        assert(test1.isInstanceOf[Int])
      }

      "returns values in the correct range" in {
        test(integerRangeGenerator(0, 100))(x =>  x>= 0 && x < 100)
        test(integerRangeGenerator(-10, 20))(x => x >= -10 && x < 20)
        test(integerRangeGenerator(100, 300))(x => x >= 100 && x < 300)
        test(integerRangeGenerator(-100, 100))(x => x >= -100 && x < 100)
      }

      "covers the range for a non-negative range" in {
        val testGenerator = integerRangeGenerator(0, 100)
        var gotLowEnd = false
        var gotHighEnd = false

        for ( _ <- 1 to 1000) {
          val x = testGenerator.generate
          assert(x>=0 && x < 100)
          if(!gotLowEnd && x == 0) gotLowEnd = true
          if(!gotHighEnd && x == 99) gotHighEnd = true
        }
        assert(gotLowEnd && gotHighEnd)
      }

      "covers the range for a negative range" in {
        val testGenerator = integerRangeGenerator(-100, -50)
        var gotLowEnd = false
        var gotHighEnd = false

        for (_ <- 1 to 1000) {
          val x = testGenerator.generate
          assert(x >= -100 && x < -50)
          if(!gotLowEnd && x == -100) gotLowEnd = true
          if(!gotHighEnd && x == -51) gotHighEnd = true
        }
        assert(gotLowEnd && gotHighEnd)
      }

      "covers the range return for ranges that span zero" in {
        val testGenerator = integerRangeGenerator(-100, 100)
        var gotLowEnd = false
        var gotHighEnd = false

        for(_ <- 1 to 1000) {
          val x = testGenerator.generate
          assert(x >= -100 && x < 100)
          if(!gotLowEnd && x == -100) gotLowEnd = true
          if(!gotHighEnd && x == 99) gotHighEnd = true
        }

        assert(gotLowEnd && gotHighEnd)
      }

      "blows up for an invalid range" in {
        assertThrows[IllegalArgumentException]{
          integerRangeGenerator(100, 10)
        }
      }
    }
  }

  "The doubleGenerator" when {
    "run" should {
      "make random doubles" in {
        val test1 = doubleGenerator.generate
        assert(test1.isInstanceOf[Double])
      }

      "not return the same in every time" in {
        val test1 = doubleGenerator.generate
        val test2 = doubleGenerator.generate
        assert(test1 != test2)
      }
    }
  }

  "The charGenerator" when {
    "run" should {
      "make random characters" in {
        val test1 = charGenerator.generate
        assert(test1.isInstanceOf[Char])
      }

      "not return the same in every time" in {
        val testChars = Set(charGenerator.generate, charGenerator.generate, charGenerator.generate, charGenerator.generate)
        // of the  4 runs we can normally expect at least two  different characters
        assert(testChars.size > 1 )
      }
    }
  }

  "The stringGenerator" when {
    "run" should {
      "make random strings" in {
        val test1 = stringGenerator.generate
        assert(test1.isInstanceOf[String])
      }

      "not return the same in every time" in {
        val testString1 = stringGenerator.generate
        assert(testString1.length >= 0 && testString1.length <= 50)
        val testString2 = stringGenerator.generate
        assert(testString2.length >= 0 && testString2.length <= 50)
        assert(testString1 != testString2)
      }
    }
  }

  "The booleanGenerator" when {
    "run" should {
      "make random booleans" in {
        val test1 = booleanGenerator.generate
        assert(test1.isInstanceOf[Boolean])
      }

      "have a roughly even distribution" in {
        var trueCount = 0
        var falseCount = 0

        (1 to 1000) foreach { _ =>
          val b = booleanGenerator.generate
          if(b) trueCount+= 1 else falseCount+= 1
        }

        assert(trueCount > 430)
        assert(falseCount > 430)
      }
    }
  }

  "The pairGenerator" when {
    "run" should {
      "make random pairs of the correct type" in {
        val (test1, test2) = pairGenerator(integerGenerator, booleanGenerator).generate
        assert(test1.isInstanceOf[Int])
        assert(test2.isInstanceOf[Boolean])
      }

      "return random values" in {
        val (test1, test2) = pairGenerator(integerGenerator, integerGenerator).generate
        assert(test1 != test2)

      }
    }
  }

  "The singleGenerator" when {
    "run" should {
      "return the correct value" in {
        val sentinelValue = 101
        val test1 = single(sentinelValue).generate
        assert(test1.isInstanceOf[Int])
        assert(test1 === sentinelValue)
      }
    }
  }

  "The oneOfGenerator" when {
    "run" should {
      "returns a correct value for variable arguments" in {
        val test1 = oneOfGenerator("red", "blue", "green").generate
        assert(Seq("red", "blue",  "green").contains(test1))
      }

      "returns a correct value for a seq" in {
        val data = Seq("red", "blue",  "green")
        val test1 = oneOfGenerator(data: _*).generate
        assert(data.contains(test1))
      }

    }
  }

  "The listGenerator" when {
    "run" should {
      "returns a correct type" in {
        val test1 = listGenerator(integerGenerator).generate
        assert(test1.isInstanceOf[List[Int]])
      }

      "not return the same in every time" in {

        var nonEmtpyListCount = 0
        var emtpyListCount = 0
        val testGenerator = listGenerator(integerGenerator)

        for (_ <- 1 to 100) {
          val x  = testGenerator.generate
          if(x.isEmpty)
            emtpyListCount += 1
          else {
            nonEmtpyListCount += 1
            assert(x.head.isInstanceOf[Int])
          }
        }

        assert(nonEmtpyListCount >= 40)
        assert(nonEmtpyListCount + emtpyListCount == 100)
      }

    }
  }

  def test[T](g: Generator[T], numTimes: Int = 100)(test: T => Boolean): Unit = {
    for(_ <- 0 until numTimes) {
      val  value = g.generate
      assert(test(value), "Test failed for " + value)
    }
  }


}
