/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusLaboralDetailComponent } from 'app/entities/estatus-laboral/estatus-laboral-detail.component';
import { EstatusLaboral } from 'app/shared/model/estatus-laboral.model';

describe('Component Tests', () => {
  describe('EstatusLaboral Management Detail Component', () => {
    let comp: EstatusLaboralDetailComponent;
    let fixture: ComponentFixture<EstatusLaboralDetailComponent>;
    const route = ({ data: of({ estatusLaboral: new EstatusLaboral(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusLaboralDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstatusLaboralDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusLaboralDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estatusLaboral).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
