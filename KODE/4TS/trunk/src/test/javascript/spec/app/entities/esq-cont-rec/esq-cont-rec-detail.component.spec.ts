/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EsqContRecDetailComponent } from 'app/entities/esq-cont-rec/esq-cont-rec-detail.component';
import { EsqContRec } from 'app/shared/model/esq-cont-rec.model';

describe('Component Tests', () => {
  describe('EsqContRec Management Detail Component', () => {
    let comp: EsqContRecDetailComponent;
    let fixture: ComponentFixture<EsqContRecDetailComponent>;
    const route = ({ data: of({ esqContRec: new EsqContRec(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EsqContRecDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EsqContRecDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EsqContRecDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.esqContRec).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
