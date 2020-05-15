package com.hamidjonhamidov.cvforkhamidjon.util


sealed class MyJob {

    class GetAboutMe : MyJob(){
        override fun equals(other: Any?): Boolean {
            return other is GetAboutMe
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class GetMySkills: MyJob(){
        override fun equals(other: Any?): Boolean =
            other is GetMySkills

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class GetAchiements: MyJob(){
        override fun equals(other: Any?): Boolean =
            other is GetAchiements

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class GetProjects: MyJob() {
        override fun equals(other: Any?): Boolean =
            other is GetProjects

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }
}





















