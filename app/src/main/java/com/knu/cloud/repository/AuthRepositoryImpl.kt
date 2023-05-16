package com.knu.cloud.repository

import com.knu.cloud.data.auth.AuthLocalDataSource
import com.knu.cloud.data.auth.AuthRemoteDataSource
import com.knu.cloud.di.*
import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.auth.LoginRequest
import com.knu.cloud.model.auth.SignUpRequest
import com.knu.cloud.network.RetrofitFailureStateException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource,
    private val sessionManager: SessionManager,
    @ApplicationScopeIO private val scopeIO: CoroutineScope
): AuthRepository {
    override suspend fun login(id : String, password : String) : Result<String>{
        return when(val authResponse = remoteDataSource.login(LoginRequest(id,password))){
            is NetworkResult.Success -> {
                if(authResponse.data.status == 200){
                    Result.success(authResponse.data.message)
                }else{
                    Result.failure(
                        RetrofitFailureStateException(
                            code = authResponse.data.status,
                            error = authResponse.data.message
                        )
                    )
                }
                // TODO : 만약 백엔드에서 success이지만 status를 404로 준다면 여기서 처리해줘야함
            }
            is NetworkResult.Error -> Result.failure(
                RetrofitFailureStateException(
                    authResponse.message,
                    authResponse.code
                )
            )
            is NetworkResult.Exception -> Result.failure(authResponse.e)
        }
    }
    override suspend fun signUp(email : String, username :String , password: String ) : Result<String>{
        return when(val authResponse = remoteDataSource.signUp(SignUpRequest(email,username,password))){
            is NetworkResult.Success -> Result.success("signup success")
            is NetworkResult.Error -> Result.failure(
                RetrofitFailureStateException(
                    authResponse.message,
                    authResponse.code
                )
            )
            is NetworkResult.Exception -> Result.failure(authResponse.e)
        }
    }
//    fun autoLogin(){
//        scope.launch {
//                // 홈에서 사용되는 API 호출
//            /*   // 만약 성공이면
//            *  isSignIn.value = true
//            * // 실패면 로그인 화면 띄우기 -> 아무 작업 안해줘도 됨 기본값 false이기 때문
//            * */
//            }
//        }
//    override suspend fun getTokenFlow() : Flow<String>{
//        // TODO : TokenFlow 제공하는 함수 구현 -> AuthInterceptor에서 사용
//        // DataStore에서 꺼내와서 주면 될거임
//        return localDataSource.getTokenFlow()
//    }
//    override suspend fun saveToken(token: Token){
//        // TODO: RemoteAuthDataSource에서 토큰 받아와서 LocalAuthDataSource의 saveToken() 수행 (자동 로그인을 위함)
//    }
}
