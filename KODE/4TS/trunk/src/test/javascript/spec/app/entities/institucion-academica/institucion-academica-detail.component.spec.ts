/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { InstitucionAcademicaDetailComponent } from 'app/entities/institucion-academica/institucion-academica-detail.component';
import { InstitucionAcademica } from 'app/shared/model/institucion-academica.model';

describe('Component Tests', () => {
  describe('InstitucionAcademica Management Detail Component', () => {
    let comp: InstitucionAcademicaDetailComponent;
    let fixture: ComponentFixture<InstitucionAcademicaDetailComponent>;
    const route = ({ data: of({ institucionAcademica: new InstitucionAcademica(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [InstitucionAcademicaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InstitucionAcademicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstitucionAcademicaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.institucionAcademica).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
