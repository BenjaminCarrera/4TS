/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { FormacionAcademicaDetailComponent } from 'app/entities/formacion-academica/formacion-academica-detail.component';
import { FormacionAcademica } from 'app/shared/model/formacion-academica.model';

describe('Component Tests', () => {
  describe('FormacionAcademica Management Detail Component', () => {
    let comp: FormacionAcademicaDetailComponent;
    let fixture: ComponentFixture<FormacionAcademicaDetailComponent>;
    const route = ({ data: of({ formacionAcademica: new FormacionAcademica(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [FormacionAcademicaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FormacionAcademicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormacionAcademicaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.formacionAcademica).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
