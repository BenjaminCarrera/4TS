/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusCandidatoDetailComponent } from 'app/entities/estatus-candidato/estatus-candidato-detail.component';
import { EstatusCandidato } from 'app/shared/model/estatus-candidato.model';

describe('Component Tests', () => {
  describe('EstatusCandidato Management Detail Component', () => {
    let comp: EstatusCandidatoDetailComponent;
    let fixture: ComponentFixture<EstatusCandidatoDetailComponent>;
    const route = ({ data: of({ estatusCandidato: new EstatusCandidato(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusCandidatoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstatusCandidatoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusCandidatoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estatusCandidato).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
