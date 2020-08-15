package project.dependency;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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

@Module
public class ApplicationModule {
    private Application application;
    private BaseApplication baseApplication;

    public ApplicationModule(BaseApplication baseApplication) {
        this.baseApplication = baseApplication;
        this.application = (Application) baseApplication;
    }

    @Provides
    @Singleton
    BaseApplication providesBaseApplication() {
        return baseApplication;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository() {
        return new UserRepository();
    }

    @Provides
    @Singleton
    UserViewModel provideUserViewModel() {
        return new UserViewModel(new Application(),new UserRepository());
    }

    @Provides
    @Singleton
    ProductRepository provideProductRepository() {
        return new ProductRepository();
    }

    @Provides
    @Singleton
    ProductViewModel provideProductViewModel() {
        return new ProductViewModel(new Application(),new ProductRepository());
    }

    @Provides
    @Singleton
    ImageRepository provideImageRepository() {
        return new ImageRepository();
    }

    @Provides
    @Singleton
    ProductImageViewModel provideProductImageViewModel() {
        return new ProductImageViewModel(new Application(),new ImageRepository());
    }

    @Provides
    @Singleton
    FactorRepository provideFactorRepository() {
        return new FactorRepository();
    }

    @Provides
    @Singleton
    FactorViewModel provideFactorViewModel() {
        return new FactorViewModel(new Application(),new FactorRepository());
    }

    @Provides
    @Singleton
    CategoryRepository provideCategoryRepository() {
        return new CategoryRepository();
    }

    @Provides
    @Singleton
    CategoryViewModel provideCategoryViewModel() {
        return new CategoryViewModel(new Application(),new CategoryRepository());
    }
}
