package com.knu.cloud.screens.auth.signup

object SignUpErrorState {
    const val EMAIL_EMPTY = "이메일을 입력해주세요"
    const val EMAIL_PATTERN_NOT_SATISFIED = "이메일 형식을 확인해주세요"
    const val PASSWORD_EMPTY = "비밀번호를 입력해주세요"
    const val PASSWORD_LENGTH_NOT_SATISFIED = "비밀번호 길이가 맞지 않습니다."
    const val PASSWORD_VALIDATION_NOT_SATISFIED = "비밀번호 형식이 맞지 않습니다"
    const val PASSWORD_NOT_SAME = "비밀번호가 일치하지 않습니다."
    const val POLICY_NOT_SATISFIED = "동의하지 않은 약관이 존재합니다."
}