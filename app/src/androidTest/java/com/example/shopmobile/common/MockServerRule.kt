package com.example.shopmobile.common

import com.example.shopmobile.annotations.RequiresMockServer
import org.junit.AssumptionViolatedException
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockServerRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                // Check if the method or the class has the annotation
                val methodAnnotation = description.getAnnotation(RequiresMockServer::class.java)
                val classAnnotation = description.testClass.getAnnotation(RequiresMockServer::class.java)

                val isMockRequired = methodAnnotation != null || classAnnotation != null

                if (isMockRequired && !TestConfig.USE_MOCK_SERVER) {
                    throw AssumptionViolatedException("Skipping test: Requires Mock Server but running against Real Backend.")
                }

                // Continue with the test execution
                base.evaluate()
            }
        }
    }
}