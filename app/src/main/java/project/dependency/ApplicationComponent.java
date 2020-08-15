package project.dependency;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import project.base.BaseActivity;
import project.base.BaseApplication;
import project.models.CategoryViewModel;
import project.models.FactorViewModel;
import project.models.ProductImageViewModel;
import project.models.ProductViewModel;
import project.models.UserViewModel;
import project.repository.CategoryRepository;
import project.repository.FactorRepository;
import project.repository.ImageRepository;
import project.repository.ProductRepository;
import project.repository.UserRepository;
import project.service.ServiceGenerator;
import project.ui.activity.FilterActivity;
import project.ui.activity.HomeActivity;
import project.ui.activity.ProductActivity;
import project.ui.activity.ProductDetailActivity;
import project.ui.activity.SearchActivity;
import project.ui.activity.SplashActivity;
import project.ui.fragment.BasketFragment;
import project.ui.fragment.CategoriesFragment;
import project.ui.fragment.ContactFragment;
import project.ui.fragment.EntrenceProfileFragment;
import project.ui.fragment.FactorFragment;
import project.ui.fragment.FavouriteFragment;
import project.ui.fragment.HomeFragment;
import project.ui.fragment.LoginFragment;
import project.ui.fragment.ProfileFragment;
import project.ui.fragment.RegisterProfileFragment;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    BaseApplication providesBaseApplication();
    Application providesApplication();

    UserRepository provideUserRepository();
    UserViewModel provideUserViewModel();

    ProductRepository provideProductRepository();
    ProductViewModel provideProductViewModel();

    ImageRepository provideImageRepository();
    ProductImageViewModel provideProductImageViewModel();

    FactorRepository provideFactorRepository();
    FactorViewModel provideFactorViewModel();

    CategoryRepository provideCategoryRepository();
    CategoryViewModel provideCategoryViewModel();

    void inject(BaseActivity baseActivity);

    void inject(SplashActivity splashActivity);

    void inject(HomeActivity homeActivity);

    void inject(HomeFragment homeFragment);

    void inject(RegisterProfileFragment registerProfileFragment);

    void inject(ProfileFragment profileFragment);

    void inject(LoginFragment loginFragment);

    void inject(FavouriteFragment favouriteFragment);

    void inject(FactorFragment factorFragment);

    void inject(EntrenceProfileFragment entrenceProfileFragment);

    void inject(ContactFragment contactFragment);

    void inject(CategoriesFragment categoriesFragment);

    void inject(BasketFragment basketFragment);

    void inject(FilterActivity filterActivity);

    void inject(ProductActivity productActivity);

    void inject(ProductDetailActivity productDetailActivity);

    void inject(SearchActivity searchActivity);
}
