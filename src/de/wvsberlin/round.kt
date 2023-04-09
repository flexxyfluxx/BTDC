package de.wvsberlin

import java.lang.IllegalArgumentException

class Round(val game: Game) {
    val waves = emptyList<Wave>().toMutableList()
    val activeWaves = HashMap<Int, Wave>()
    val waveKeyGen = Counter()
    var reward: Int = 0

    companion object {
        /**
         * all the rounds. all of em.
         *
         * There is no round that is not contained within.
         */
        @JvmStatic
        fun ROUNDS(game: Game): Array<Round> = arrayOf(
                Round(game)  // 1
                        .addWave {
                            Wave(game, it)
                                    .setCount(20)
                                    .setSpacing(100)
                        }
                        .addReward(101)
                ,
                Round(game)  // 2
                        .addWave {
                            Wave(game, it)
                                    .setCount(35)
                                    .setSpacing(75)
                        }
                        .addReward(102)
                ,
                Round(game)  // 3
                        .addWave {
                            Wave(game, it)
                                    .setCount(10)
                                    .setSpacing(80)
                        }
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::BLUE)
                                    .setCount(5)
                                    .setSpacing(80)
                                    .setWaitsForLastRoundToBeFullySent()
                        }
                        .addWave {
                            Wave(game, it)
                                    .setCount(15)
                                    .setSpacing(80)
                                    .setWaitsForLastRoundToBeFullySent()
                        }
                        .addReward(103)
                ,
                Round(game)  // 4
                        .addWave {
                            Wave(game, it)
                                    .setCount(10)
                                    .setSpacing(60)
                        }
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::BLUE)
                                    .setCount(18)
                                    .setSpacing(15)
                                    .setWaitsForLastRoundToBeFullySent()
                        }
                        .addWave {
                            Wave(game, it)
                                    .setCount(15)
                                    .setSpacing(15)
                                    .setWaitsForLastRoundToBeFullySent()
                        }
                        .addReward(104)
                ,
                Round(game)  // 5
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::BLUE)
                                    .setCount(27)
                                    .setSpacing(50)
                        }
                        .addWave {
                            Wave(game, it)
                                    .setCount(5)
                                    .setSpacing(150)
                                    .setStartDelay(500)
                        }
                        .addReward(105)
                ,
                Round(game)  // 6
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::GREEN)
                                    .setCount(4)
                                    .setSpacing(50)
                        }
                        .addWave {
                            Wave(game, it)
                                    .setCount(15)
                                    .setSpacing(30)
                                    .setStartDelay(150)
                                    .setWaitsForLastRoundToBeFullySent()
                        }
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::BLUE)
                                    .setCount(15)
                                    .setSpacing(30)
                                    .setStartDelay(100)
                                    .setWaitsForLastRoundToBeFullySent()
                        }
                        .addReward(106)
                ,
                Round(game)  // 7
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::BLUE)
                                    .setCount(10)
                                    .setSpacing(30)
                        }
                        .addWave {
                            Wave(game, it)
                                    .setCount(20)
                                    .setSpacing(30)
                                    .setStartDelay(100)
                                    .setWaitsForLastRoundToBeFullySent()
                        }
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::GREEN)
                                    .setCount(5)
                                    .setSpacing(50)
                                    .setStartDelay(150)
                                    .setWaitsForLastRoundToBeFullySent()
                        }
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::BLUE)
                                    .setCount(10)
                                    .setSpacing(30)
                                    .setStartDelay(150)
                                    .setWaitsForLastRoundToBeFullySent()
                        }
        )
        fun ROUNDS_DEBUG(game: Game): Array<Round> = arrayOf(
                Round(game)
                        .addWave {
                            Wave(game, it)
                        }
                ,
                Round(game)
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::BLUE)
                        }
                ,
                Round(game)
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::GREEN)
                        }
                ,
                Round(game)
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::YELLOW)
                        }
                ,
                Round(game)
                        .addWave {
                            Wave(game, it)
                                    .setEnemyType(Enemy::PINK)
                        }
        )
    }

    /**
     * add a wave to the round. self-explanatory, hopefully.
     *
     * method chaining is fun or sth.
     */
    fun addWave(waveSupplier: WaveSupplier): Round {
        waves.add(waveSupplier(waveKeyGen.next()))
        return this
    }

    /**
     * add a reward. if this puzzles you, idk what to tell you.
     */
    fun addReward(reward: Int): Round {
        this.reward = reward
        return this
    }

    /**
     * This function will run every game tick.
     *
     * It does things like send waves at appropriate times.
     *
     * The boolean return value indicates whether the round has ended.
     */
    fun tick(): Boolean {
        if (waves.isEmpty() && activeWaves.isEmpty())
            return true

        if (waves.isNotEmpty()) {
            if (waves[0].startDelay <= 0) {
                if (!waves[0].waitsForRoundToBeFullySent or activeWaves.isEmpty()) {
                    val newWave = waves.removeAt(0)
                    activeWaves[newWave.key] = newWave
                }
            } else {
                waves[0].startDelay--
            }
        }

        for (wave in activeWaves.values) {
            if (wave.count <= 0) {
                activeWaves.remove(wave.key)
                continue
            }

            wave.tick()
        }

        return false
    }
}

typealias WaveSupplier = (key: Int) -> Wave

class Wave(val game: Game, val key: Int) {
    var startDelay: Int = 0
    var spacing: Int = 0
    var sendCooldown: Int = 0
    var count: Int = 1
    var enemySupplier: EnemySupplier = (Enemy)::WEAKEST
    var waitsForRoundToBeFullySent: Boolean = false

    /**
     * This fun is called every tick, if the wave is active, by the parent Round.
     *
     * Sends enemies when appropriate.
     */
    fun tick() {
        if (sendCooldown > 0) {
            sendCooldown--
            return
        }

        count--
        game.spawnEnemy(enemySupplier)
        sendCooldown = spacing
    }

    /**
     * Set the enemy type by providing an appropriate supplier.
     */
    fun setEnemyType(enemySupplier: EnemySupplier): Wave {
        this.enemySupplier = enemySupplier
        return this
    }

    /**
     * Set delay in ticks before first enemy is sent.
     *
     * If waitsForLastRoundToBeFullySent is true, this will begin to count down once there are no actively sending waves.
     *
     * Else, this will begin to count down as soon as the previous wave starts sending.
     */
    fun setStartDelay(delay: Int): Wave {
        if (delay < 0) throw IllegalArgumentException("Delay must not be negative!")

        startDelay = delay
        return this
    }

    /**
     * Set the amount of ticks that are waited between individual enemy sends.
     */
    fun setSpacing(spacing: Int): Wave {
        if (spacing < 0) throw IllegalArgumentException("Spacing must not be negative!")

        this.spacing = spacing
        return this
    }

    /**
     * Set the amount of enemies in the wave.
     */
    fun setCount(count: Int): Wave {
        if (count < 0) throw IllegalArgumentException("Count must not be negative!")

        this.count = count
        return this
    }

    /**
     * If this is set, the wave will only activate after all previous waves have finished.
     */
    fun setWaitsForLastRoundToBeFullySent(): Wave {
        waitsForRoundToBeFullySent = true
        return this
    }
}
