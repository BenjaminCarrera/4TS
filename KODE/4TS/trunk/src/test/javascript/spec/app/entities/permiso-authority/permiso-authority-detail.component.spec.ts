/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { PermisoAuthorityDetailComponent } from 'app/entities/permiso-authority/permiso-authority-detail.component';
import { PermisoAuthority } from 'app/shared/model/permiso-authority.model';

describe('Component Tests', () => {
  describe('PermisoAuthority Management Detail Component', () => {
    let comp: PermisoAuthorityDetailComponent;
    let fixture: ComponentFixture<PermisoAuthorityDetailComponent>;
    const route = ({ data: of({ permisoAuthority: new PermisoAuthority(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [PermisoAuthorityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PermisoAuthorityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PermisoAuthorityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.permisoAuthority).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
