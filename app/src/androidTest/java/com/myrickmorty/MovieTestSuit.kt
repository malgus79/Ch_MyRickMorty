package com.myrickmorty

import com.myrickmorty.model.local.AppDatabaseTest
import com.myrickmorty.model.local.RickMortyDaoTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ExampleInstrumentedTest::class,
    AppDatabaseTest::class,
    RickMortyDaoTest::class
)
class MovieTestSuit