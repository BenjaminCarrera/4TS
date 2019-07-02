/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusAcademicoDetailComponent } from 'app/entities/estatus-academico/estatus-academico-detail.component';
import { EstatusAcademico } from 'app/shared/model/estatus-academico.model';

describe('Component Tests', () => {
  describe('EstatusAcademico Management Detail Component', () => {
    let comp: EstatusAcademicoDetailComponent;
    let fixture: ComponentFixture<EstatusAcademicoDetailComponent>;
    const route = ({ data: of({ estatusAcademico: new EstatusAcademico(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusAcademicoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstatusAcademicoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusAcademicoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estatusAcademico).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
