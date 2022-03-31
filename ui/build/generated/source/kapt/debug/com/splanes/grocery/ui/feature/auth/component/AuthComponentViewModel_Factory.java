package com.splanes.grocery.ui.feature.auth.component;

import com.splanes.grocery.domain.feature.user.usecase.FetchUserUseCase;
import com.splanes.grocery.domain.feature.user.usecase.IsUserSignUpUseCase;
import com.splanes.grocery.domain.feature.user.usecase.SignInUseCase;
import com.splanes.grocery.domain.feature.user.usecase.SignUpUseCase;
import com.splanes.grocery.ui.feature.auth.viewmodel.AuthComponentViewModel;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AuthComponentViewModel_Factory implements Factory<AuthComponentViewModel> {
  private final Provider<IsUserSignUpUseCase> isUserSignUpUseCaseProvider;

  private final Provider<FetchUserUseCase> fetchUserProvider;

  private final Provider<SignInUseCase> signInUseCaseProvider;

  private final Provider<SignUpUseCase> signUpUseCaseProvider;

  public AuthComponentViewModel_Factory(Provider<IsUserSignUpUseCase> isUserSignUpUseCaseProvider,
      Provider<FetchUserUseCase> fetchUserProvider, Provider<SignInUseCase> signInUseCaseProvider,
      Provider<SignUpUseCase> signUpUseCaseProvider) {
    this.isUserSignUpUseCaseProvider = isUserSignUpUseCaseProvider;
    this.fetchUserProvider = fetchUserProvider;
    this.signInUseCaseProvider = signInUseCaseProvider;
    this.signUpUseCaseProvider = signUpUseCaseProvider;
  }

  @Override
  public AuthComponentViewModel get() {
    return newInstance(isUserSignUpUseCaseProvider.get(), fetchUserProvider.get(), signInUseCaseProvider.get(), signUpUseCaseProvider.get());
  }

  public static AuthComponentViewModel_Factory create(
      Provider<IsUserSignUpUseCase> isUserSignUpUseCaseProvider,
      Provider<FetchUserUseCase> fetchUserProvider, Provider<SignInUseCase> signInUseCaseProvider,
      Provider<SignUpUseCase> signUpUseCaseProvider) {
    return new AuthComponentViewModel_Factory(isUserSignUpUseCaseProvider, fetchUserProvider, signInUseCaseProvider, signUpUseCaseProvider);
  }

  public static AuthComponentViewModel newInstance(IsUserSignUpUseCase isUserSignUpUseCase,
      FetchUserUseCase fetchUser, SignInUseCase signInUseCase, SignUpUseCase signUpUseCase) {
    return new AuthComponentViewModel(isUserSignUpUseCase, fetchUser, signInUseCase, signUpUseCase);
  }
}
